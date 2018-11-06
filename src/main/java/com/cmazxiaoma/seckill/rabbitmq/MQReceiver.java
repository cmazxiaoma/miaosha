package com.cmazxiaoma.seckill.rabbitmq;

import com.cmazxiaoma.seckill.model.MiaoshaOrder;
import com.cmazxiaoma.seckill.model.MiaoshaUser;
import com.cmazxiaoma.seckill.redis.RedisService;
import com.cmazxiaoma.seckill.service.GoodsService;
import com.cmazxiaoma.seckill.service.MiaoshaService;
import com.cmazxiaoma.seckill.service.OrderService;
import com.cmazxiaoma.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/6/4 13:47
 */
@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveMiaoshaMessageDirect(String message) {
        log.info("receive direct miaosha message = {}", message);
        MiaoshaMessage miaoshaMessage = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser miaoshaUser = miaoshaMessage.getMiaoshaUser();
        Long goodsId = miaoshaMessage.getGoodsId();
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();

        if (stock <= 0) {
            return;
        }
        //判断是否已经秒杀过
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(miaoshaUser.getId(), goodsId);

        if (!Objects.isNull(order)) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(miaoshaUser, goodsVo);
    }

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receiveDirect(String message) {
        log.info("receive direct message = {}", message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info("receive topic queue1 message = {}", message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info("receive topic queue2 message = {}", message);
    }

    @RabbitListener(queues = MQConfig.HEADER_QUEUE)
    public void receiveHeader(byte[] message) {
        log.info("receive header message = {}", new String(message));
    }
}
