package com.cmazxiaoma.seckill.controller;

import com.cmazxiaoma.seckill.model.MiaoshaUser;
import com.cmazxiaoma.seckill.redis.GoodsKey;
import com.cmazxiaoma.seckill.redis.RedisService;
import com.cmazxiaoma.seckill.result.Result;
import com.cmazxiaoma.seckill.service.GoodsService;
import com.cmazxiaoma.seckill.service.MiaoshaUserService;
import com.cmazxiaoma.seckill.vo.GoodDetailVo;
import com.cmazxiaoma.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.io.IOException;
import java.util.List;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 16:35
 */
@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController {

    @Autowired
    private MiaoshaUserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    private ApplicationContext applicationContext;

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

    @RequestMapping(value = "/to_detail2/{goodsId}", produces = "text/html;charset=UTF-8")
    public String detail2(MiaoshaUser miaoshaUser, @PathVariable("goodsId") Long goodsId) {
        modelMap.addAttribute("user", miaoshaUser);
        //取缓存
        String htmlCached = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);

        if (!StringUtils.isEmpty(htmlCached)) {
            return htmlCached;
        }
        //手动渲染
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        modelMap.addAttribute("goods", goodsVo);

        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        //秒杀还没有开始
        if (now < startAt) {
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {
            //秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        modelMap.addAttribute("miaoshaStatus", miaoshaStatus);
        modelMap.addAttribute("remainSeconds", remainSeconds);
        SpringWebContext springWebContext = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), modelMap, applicationContext);
        String html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", springWebContext);

        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }
        return html;
    }

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodDetailVo> detail(MiaoshaUser miaoshaUser, @PathVariable("goodsId") Long goodsId) {
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;

        if (now < startAt) {
            //秒杀未开始
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {
            //秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodDetailVo goodDetailVo = new GoodDetailVo();
        goodDetailVo.setGoods(goodsVo);
        goodDetailVo.setUser(miaoshaUser);
        goodDetailVo.setRemainSeconds(remainSeconds);
        goodDetailVo.setMiaoshaStatus(miaoshaStatus);

        return Result.success(goodDetailVo);
    }

}
