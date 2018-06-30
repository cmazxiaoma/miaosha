package com.cmazxiaoma.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/6/21 17:04
 */
@Controller
@RequestMapping("/sessionAttributes")
@SessionAttributes(value = {"cmazxiaoma"})
public class SessionAttributesController extends BaseController {

    @RequestMapping("/test1")
    public String test() {
        modelMap.put("cmazxiaoma", "帅气");
        return "session_attributes";
    }

    @RequestMapping("/test2")
    public String test2() {
        return "session_attributes";
    }
}
