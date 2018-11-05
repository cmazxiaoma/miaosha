### jianshu blog url: https://www.jianshu.com/p/edd64fe6866a

## 抽离出商城业务：商品列表、商品详情、商品秒杀

1.我们大致优化的点是秒杀接口：redis预减库存，减少数据库访问；内存标记较少redis的访问；rabbitmq队列缓冲，异步下单，增强用户体验。那么具体步骤如下。
>1.处理秒杀业务的Controller在Spring容器周期内加载就绪。也就是实现InitializingBean，在afterPropertiesSet()方法中把商品库存加载到redis中，并且设置在内存中设置商品是否秒杀结束的flag。
```
    /**
     * 内存标记初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();

        if (CollectionUtils.isEmpty(goodsVoList)) {
            return;
        }

        goodsVoList.forEach(goodsVo -> {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goodsVo.getId(), goodsVo.getStockCount());
            localOverMap.put(goodsVo.getId(), false);
        });
    }
```
>2.后台收到秒杀请求，首先查看内存flag标记，然后减少redis中的商品库存。如果商品秒杀结束，在内存中设置秒杀结束的flag。如果商品秒杀还在进行中，那么进入下一步。

> 3.把秒杀商品的消息进行入队缓冲，直接返回。这里并不是返回成功，而是返回到排队中。此时，前台不能直接提示秒杀成功，而是启动定时器，过一段时间再去查看是否成功。

> 4.消息出队，修改db中的库存，创建秒杀订单。

2.分布式Session的解决方案是生成唯一token，token标识用户，把token写到Cookie中，然后把token+用户信息写进Redis，token在redis的失效时间要和Cookie失效时间保持一致。每当用户登录一次，要延迟Session的有效期和Cookie有效期。

3.从缓存的角度来说，我们可以进行页面缓存+URL缓存+对象缓存来达到优化的目的。我们可以手动渲染Thymeleaf模板，把商品详情页和商品列表页缓存到redis中，这里用商品列表页举例。
```
    @RequestMapping(value = "/to_list", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String list(MiaoshaUser miaoshaUser) throws IOException {
        modelMap.addAttribute("user", miaoshaUser);
        //取缓存
        String htmlCached = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(htmlCached)) {
            return htmlCached;
        }
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        modelMap.addAttribute("goodsList", goodsVoList);
        SpringWebContext springWebContext = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), modelMap, applicationContext);
        String html = thymeleafViewResolver.getTemplateEngine().process("goods_list", springWebContext);

        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }
```

4.从静态资源角度考虑，我们进行页面静态化、前后端分离、静态资源优化、CDN节点优化。这里用静态资源优化举例。
> 1.JS/CSS压缩、减少流量。
  2.多个JS/CSS组合，减少连接数
  3.CDN就近访问，减少请求时间。
  4.将一些界面缓存到用户的浏览器中。

5.安全优化。密码两次加盐，第一次加盐是固定的，写在Java代码的。第二次加盐是随机的，存储在数据库中。在商品秒杀页，添加数学公式验证码,分散用户的请求。对接口加入限流防刷机制。这里以接口限流防刷机制举例。
> 1.定义AccessLimit注解，作用于方法。
```
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {

    int seconds();

    int maxCount();

    boolean needLogin() default true;
}
```

> 2.定义AccessInterceptor拦截器，获得方法中AccessLimit注解中的参数。请求的reqeusturi作为redis中的key，seconds作为key的失效时间。每次请求加1，如果在指定时间内访问该url的次数超过设置的maxCount，那么返回“访问太频繁”。
```
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            MiaoshaUser user = getUser(request, response);
            UserContext.setUser(user);
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);

            if (Objects.isNull(accessLimit)) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();

            if (needLogin) {
                if (Objects.isNull(user)) {
                    render(response, CodeMsg.SESSION_ERROR);
                    return false;
                }
            }

            AccessKey ak = AccessKey.withExpire(seconds);
            Integer count = redisService.get(ak, key, Integer.class);

            if (Objects.isNull(count)) {
                redisService.set(ak, key, 1);
            } else if (count < maxCount) {
                redisService.incr(ak, key);
            } else {
                render(response, CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }
```
6.部署优化。LVS+Keepalived双机热备模式+Nginx+Tomcat。
