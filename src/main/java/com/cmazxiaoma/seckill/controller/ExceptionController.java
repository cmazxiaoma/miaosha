package com.cmazxiaoma.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/6/30 15:45
 */
@Controller
@RequestMapping("/exception")
public class ExceptionController {


    @GetMapping(value = "/test")
    public String resolveException() {
        Integer integer = Integer.parseInt(null);
        return "model_attribute";
    }

}
