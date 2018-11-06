package com.cmazxiaoma.seckill.cap;

import com.alibaba.fastjson.JSON;
import com.cmazxiaoma.seckill.cap.constant.Constants;
import com.cmazxiaoma.seckill.cap.dao.BrokerMessageLogMapper;
import com.cmazxiaoma.seckill.cap.model.BrokerMessageLog;
import com.cmazxiaoma.seckill.cap.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/5 22:35
 */
@Component
public class RetryMessageTask {

    @Autowired
    private RabbitmqOrderSender rabbitmqOrderSender;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void trySendMessage() {
        System.out.println("定时投递status为0的消息...");
        List<BrokerMessageLog> brokerMessageLogList = brokerMessageLogMapper.listStatusAndTimeoutMessage();
        brokerMessageLogList.forEach(brokerMessageLog -> {
            if (brokerMessageLog.getTryCount() >= 3) {
                brokerMessageLogMapper.updateMessageLogStatus(brokerMessageLog.getMessageId(),
                        Constants.ORDER_SEND_FAIL,
                        new Date());
            } else {
                brokerMessageLogMapper.updateReSendMessage(brokerMessageLog.getMessageId(),
                        new Date());
                Order order = JSON.parseObject(brokerMessageLog.getMessage(), Order.class);
                try {
                    rabbitmqOrderSender.sendOrder(order);
                } catch (Exception e) {
                    System.out.println("重新投递消息发送异常...:" + e.getMessage());
                }
            }
        });
    }
}
