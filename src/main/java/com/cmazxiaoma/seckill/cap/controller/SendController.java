package com.cmazxiaoma.seckill.cap.controller;

import com.cmazxiaoma.seckill.cap.model.Order;
import com.cmazxiaoma.seckill.cap.service.RabbitmqOrderService;
import com.cmazxiaoma.seckill.core.ResultVo;
import com.cmazxiaoma.seckill.core.ResultVoGenerator;
import com.cmazxiaoma.seckill.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/7 9:21
 */
@RestController
@RequestMapping("/send")
public class SendController {

    @Autowired
    private RabbitmqOrderService rabbitmqOrderService;

    @GetMapping("/test")
    public ResultVo send(@RequestParam(name = "name") String name) {
        Order order = new Order();
        order.setId(UUIDUtil.uuid());
        order.setName(name);
        order.setMessageId(UUIDUtil.uuid());
        rabbitmqOrderService.createOrder(order);
        return ResultVoGenerator.genSuccessResult();
    }
}
