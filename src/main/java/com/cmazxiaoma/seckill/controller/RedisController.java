package com.cmazxiaoma.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.cmazxiaoma.seckill.redis.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/10 11:22
 */
@RestController
@RequestMapping("/redis")
public class RedisController extends BaseController {

    @Autowired
    private RedisConfig redisConfig;

    @GetMapping("/info")
    public Object info() {
        return JSON.toJSON(redisConfig);
    }

}
