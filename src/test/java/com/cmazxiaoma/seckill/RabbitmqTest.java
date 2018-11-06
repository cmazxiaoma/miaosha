package com.cmazxiaoma.seckill;

import com.cmazxiaoma.seckill.cap.RetryMessageTask;
import com.cmazxiaoma.seckill.cap.model.Order;
import com.cmazxiaoma.seckill.cap.service.RabbitmqOrderService;
import com.cmazxiaoma.seckill.util.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/6 10:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqTest {

    @Autowired
    private RabbitmqOrderService rabbitmqOrderService;

    @Autowired
    private RetryMessageTask retryMessageTask;

    @Test
    public void test() {
        Order order = new Order();
        order.setId("7");
        order.setName("cmazxiaoma测试订单-7");
        order.setMessageId(UUIDUtil.uuid());

        rabbitmqOrderService.createOrder(order);
    }
}
