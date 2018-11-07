package com.cmazxiaoma.seckill;

import com.cmazxiaoma.seckill.cap.RetryMessageTask;
import com.cmazxiaoma.seckill.cap.dao.BrokerMessageLogMapper;
import com.cmazxiaoma.seckill.cap.model.BrokerMessageLog;
import com.cmazxiaoma.seckill.cap.model.Order;
import com.cmazxiaoma.seckill.cap.service.RabbitmqOrderService;
import com.cmazxiaoma.seckill.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/6 10:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RabbitmqTest {

    @Autowired
    private RabbitmqOrderService rabbitmqOrderService;

    @Autowired
    private RetryMessageTask retryMessageTask;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Test
    public void test() {
        Order order = new Order();
        order.setId("36");
        order.setName("cmazxiaoma测试订单-36");
        order.setMessageId(UUIDUtil.uuid());

        rabbitmqOrderService.createOrder(order);
    }

    @Test
    public void test2() {
        List<BrokerMessageLog> brokerMessageLogList = brokerMessageLogMapper.listStatusAndTimeoutMessage();
        log.info("list = {}", brokerMessageLogList.toString());
    }
}
