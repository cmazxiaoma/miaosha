package com.cmazxiaoma.seckill.controller;

import com.cmazxiaoma.seckill.domain.MiaoshaUser;
import com.cmazxiaoma.seckill.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/9/27 11:09
 */
@RestController
@RequestMapping("/rest/modelAttribute")
public class RestModelAttributeController {

    @GetMapping("/test")
    public Result test(@ModelAttribute(value = "miaoshaUser") MiaoshaUser miaoshaUser) {
        Result result = Result.success(0);
        return result;
    }
}
