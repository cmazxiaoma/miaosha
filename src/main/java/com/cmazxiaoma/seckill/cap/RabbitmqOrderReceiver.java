package com.cmazxiaoma.seckill.cap;

import com.cmazxiaoma.seckill.cap.model.Order;
import com.cmazxiaoma.seckill.rabbitmq.MQConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/5 23:07
 */
@Component
@Slf4j
public class RabbitmqOrderReceiver {

    @RabbitListener(queues = MQConfig.ORDER_QUEUE)
    public void receive(@Payload Order order, Channel channel,
                        @Headers Map<String, Object> headers,
                        Message message) throws IOException, InterruptedException {
        log.info("消费端接收消息...");
        log.info("message=" + message.toString());
        log.info("order=" + order);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        log.info("deliveryTag=" + deliveryTag);
        // 手工ack
        // channel.basicAck(deliveryTag, false);
    }
}
