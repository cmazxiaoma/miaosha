package com.cmazxiaoma.seckill.cap;

import com.cmazxiaoma.seckill.cap.constant.Constants;
import com.cmazxiaoma.seckill.cap.dao.BrokerMessageLogMapper;
import com.cmazxiaoma.seckill.cap.model.Order;
import com.cmazxiaoma.seckill.rabbitmq.MQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.AMQImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.RabbitResourceHolder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/5 22:16
 */
@Component
@Slf4j
public class RabbitmqOrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    // 消息发送到交换器Exchange后触发回调
    private final RabbitTemplate.ConfirmCallback confirmCallback =
            new RabbitTemplate.ConfirmCallback() {
                @Override
                public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                    log.info("生产端confirm...");
                    log.info("correlationData=" + correlationData);
                    String messageId = correlationData.getId();
                    if (ack) {
                        //confirm返回成功,更新消息投递状态
                        brokerMessageLogMapper.updateMessageLogStatus(messageId, Constants.ORDER_SEND_SUCCESS, new Date());
                    } else {
                        // 失败则进行具体的后续操作，重试或者补偿等手段。
                        log.info("ack=" + ack);
                        log.info("异常处理...");
                    }
                }
            };

    // 如果消息从交换器发送到对应队列失败时触发
    private final RabbitTemplate.ReturnCallback returnCallback =
            new RabbitTemplate.ReturnCallback() {
                @Override
                public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                    log.info("message=" + message.toString());
                    log.info("replyCode=" + replyCode);
                    log.info("replyText=" + replyText);
                    log.info("exchange=" + exchange);
                    log.info("routingKey=" + routingKey);
                }
            };

    public void sendOrder(Order order) {
        log.info("生产端发送消息...");
        rabbitTemplate.setConfirmCallback(this.confirmCallback);
        rabbitTemplate.setReturnCallback(this.returnCallback);
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        rabbitTemplate.convertAndSend(MQConfig.ORDER_DIRECT_EXCAHNGE,
                MQConfig.ORDER_QUEUE,  order, correlationData);
    }
}
