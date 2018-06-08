package com.cmazxiaoma.seckill.controller;

import com.cmazxiaoma.seckill.rabbitmq.MQSender;
import com.cmazxiaoma.seckill.redis.RedisService;
import com.cmazxiaoma.seckill.result.Result;
import com.cmazxiaoma.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 16:36
 */
@Controller
@RequestMapping("/rabbitmq")
public class RabbitmqController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;

    @GetMapping("/header")
    @ResponseBody
    public Result<String> header() {
        mqSender.sendHeader("hello, header");
        return Result.success("hello, header");
    }

    @GetMapping("/fanout")
    @ResponseBody
    public Result<String> fanout() {
        mqSender.sendFanout("hello, fanout");
        return Result.success("hello, fanout");
    }

    @GetMapping("/topic")
    @ResponseBody
    public Result<String> topic() {
        mqSender.sendTopic("hello, topic");
        return Result.success("hello, topic");
    }

    @GetMapping("/direct")
    @ResponseBody
    public Result<String> direct() {
        mqSender.sendDirect("hello, direct");
        return Result.success("hello, direct");
    }
}
