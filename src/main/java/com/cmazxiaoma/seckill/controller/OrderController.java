package com.cmazxiaoma.seckill.controller;

import com.cmazxiaoma.seckill.domain.MiaoshaUser;
import com.cmazxiaoma.seckill.domain.OrderInfo;
import com.cmazxiaoma.seckill.redis.RedisService;
import com.cmazxiaoma.seckill.result.CodeMsg;
import com.cmazxiaoma.seckill.result.Result;
import com.cmazxiaoma.seckill.service.GoodsService;
import com.cmazxiaoma.seckill.service.MiaoshaUserService;
import com.cmazxiaoma.seckill.service.OrderService;
import com.cmazxiaoma.seckill.vo.GoodsVo;
import com.cmazxiaoma.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 16:35
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(MiaoshaUser user, @RequestParam("orderId") Long orderId) {
        if (Objects.isNull(user)) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo orderInfo = orderService.getOrderById(orderId);

        if (Objects.isNull(orderInfo)) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        Long goodsId = orderInfo.getGoodsId();
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrder(orderInfo);
        orderDetailVo.setGoods(goodsVo);

        return Result.success(orderDetailVo);
    }
}
