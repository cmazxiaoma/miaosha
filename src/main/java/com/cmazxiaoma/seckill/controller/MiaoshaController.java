package com.cmazxiaoma.seckill.controller;

import com.cmazxiaoma.seckill.access.AccessLimit;
import com.cmazxiaoma.seckill.model.MiaoshaOrder;
import com.cmazxiaoma.seckill.model.MiaoshaUser;
import com.cmazxiaoma.seckill.rabbitmq.MQSender;
import com.cmazxiaoma.seckill.rabbitmq.MiaoshaMessage;
import com.cmazxiaoma.seckill.redis.GoodsKey;
import com.cmazxiaoma.seckill.redis.MiaoshaKey;
import com.cmazxiaoma.seckill.redis.OrderKey;
import com.cmazxiaoma.seckill.redis.RedisService;
import com.cmazxiaoma.seckill.result.CodeMsg;
import com.cmazxiaoma.seckill.result.Result;
import com.cmazxiaoma.seckill.service.GoodsService;
import com.cmazxiaoma.seckill.service.MiaoshaService;
import com.cmazxiaoma.seckill.service.MiaoshaUserService;
import com.cmazxiaoma.seckill.service.OrderService;
import com.cmazxiaoma.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 16:35
 */
@Controller
@RequestMapping("/miaosha")
@Slf4j
public class MiaoshaController extends BaseController implements InitializingBean {

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private MQSender mqSender;

    private Map<Long, Boolean> localOverMap = new HashMap<>();

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

    @GetMapping(value = "/reset")
    @ResponseBody
    public Result<Boolean> reset() {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();

        goodsVoList.forEach(goodsVo -> {
            goodsVo.setStockCount(10);
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goodsVo.getId(), 10);
            localOverMap.put(goodsVo.getId(), false);
        });
        redisService.delete(OrderKey.getMiaoshaOrderByUidGid);
        redisService.delete(MiaoshaKey.isGoodsOver);
        miaoshaService.reset(goodsVoList);

        return Result.success(true);
    }

    @PostMapping(value = "/do_miaosha/{path}")
    @ResponseBody
    public Result<Integer> miaosha(MiaoshaUser miaoshaUser,
                                   @RequestParam("goodsId") Long goodsId,
                                   @PathVariable("path") String path) {
        modelMap.addAttribute("user", miaoshaUser);

        if (Objects.isNull(miaoshaUser)) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //验证path
        boolean check = miaoshaService.checkPath(miaoshaUser, goodsId, path);

        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);

        if (over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);

        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //判断重复秒杀
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(miaoshaUser.getId(), goodsId);

        if (!Objects.isNull(miaoshaOrder)) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //入队
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
        miaoshaMessage.setMiaoshaUser(miaoshaUser);
        miaoshaMessage.setGoodsId(goodsId);
        mqSender.sendMiaoshaMessageDirect(miaoshaMessage);

        return Result.success(0);
    }

    /**
     * @param miaoshaUser
     * @param goodsId
     * @return orderId，秒杀成功
     * -1:秒杀失败
     *  0:排队中
     */
    @GetMapping("/result")
    @ResponseBody
    public Result<Long> miaoshaResult(MiaoshaUser miaoshaUser,
                                      @RequestParam("goodsId") Long goodsId) {
        modelMap.addAttribute("user", miaoshaUser);

        if (Objects.isNull(miaoshaUser)) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        Long result = miaoshaService.getMiaoshaResult(miaoshaUser.getId(), goodsId);

        return Result.success(result);
    }

    @GetMapping(value = "/path")
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @ResponseBody
    public Result<String> getMiaoshaPath(MiaoshaUser miaoshaUser,
                                         @RequestParam("goodsId") Long goodsId,
                                         @RequestParam(value = "verifyCode", defaultValue = "0") Integer verifyCode) {
        if (Objects.isNull(miaoshaUser)) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        boolean check = miaoshaService.checkVerifyCode(miaoshaUser, goodsId, verifyCode);

        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        String path = miaoshaService.createMiaoshaPath(miaoshaUser, goodsId);

        return Result.success(path);
    }

    @GetMapping(value = "/verifyCode")
    @ResponseBody
    public Result<String> getMiaoshaVerifyCode(MiaoshaUser miaoshaUser,
                                               @RequestParam("goodsId") Long goodsId) {
        if (Objects.isNull(miaoshaUser)) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        try {
            BufferedImage image = miaoshaService.createVerifyCode(miaoshaUser, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();

            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(CodeMsg.MIAOSHA_FAIL);
        }
    }
}
