package com.cmazxiaoma.seckill.cap.service;

import com.alibaba.fastjson.JSON;
import com.cmazxiaoma.seckill.cap.model.BrokerMessageLog;
import com.cmazxiaoma.seckill.cap.model.Order;
import com.cmazxiaoma.seckill.cap.RabbitmqOrderSender;
import com.cmazxiaoma.seckill.cap.constant.Constants;
import com.cmazxiaoma.seckill.cap.dao.BrokerMessageLogMapper;
import com.cmazxiaoma.seckill.cap.dao.OrderMapper;
import com.cmazxiaoma.seckill.core.AbstractService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/5 22:07
 */
@Service
@Transactional
public class RabbitmqOrderService extends AbstractService<Order> {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Autowired
    private RabbitmqOrderSender rabbitmqOrderSender;

    public void createOrder(Order order) {
        Date orderTime = new Date();
        orderMapper.insert(order);
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        brokerMessageLog.setMessageId(order.getMessageId());
        brokerMessageLog.setMessage(JSON.toJSONString(order));
        brokerMessageLog.setTryCount(0);
        brokerMessageLog.setStatus(Constants.ORDER_SENDING);
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(orderTime, Constants.ORDER_TIMEOUT));
        brokerMessageLog.setCreateTime(new Date());
        brokerMessageLog.setUpdateTime(new Date());
        brokerMessageLogMapper.insert(brokerMessageLog);
        rabbitmqOrderSender.sendOrder(order);
    }
}
