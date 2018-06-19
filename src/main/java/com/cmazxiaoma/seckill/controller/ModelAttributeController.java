package com.cmazxiaoma.seckill.controller;

import com.cmazxiaoma.seckill.domain.MiaoshaUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/6/17 16:06
 */
@RequestMapping("/modelAttribute")
@Controller
public class ModelAttributeController extends BaseController {


    @ModelAttribute(value = "miaoshaUser")
    @RequestMapping("/test")
    public String modelAttribute(MiaoshaUser miaoshaUser) {
        return "login";
    }
}
