package com.cmazxiaoma.seckill.cap;

import com.cmazxiaoma.seckill.cap.model.Order;
import com.cmazxiaoma.seckill.rabbitmq.MQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/5 23:07
 */
@Component
public class RabbitmqOrderReceiver {

    @RabbitListener(queues = MQConfig.ORDER_QUEUE)
    public void receive(@Payload Order order, com.rabbitmq.client.Channel channel,
                        @Headers Map<String, Object> headers) throws IOException {
        System.out.println("消费端接收消息...");
        System.out.println("order=" + order);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("deliveryTag=" + deliveryTag);
        // 手工ack
        channel.basicAck(deliveryTag, false);
    }
}
