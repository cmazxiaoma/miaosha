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
@Controller
@RequestMapping("/modelAttribute")
public class ModelAttributeController extends BaseController {


    /**
     *
     * @param miaoshaUser
     * @return
     */
    @RequestMapping("/test1")
    public String test1(@ModelAttribute(value = "miaoshaUser") MiaoshaUser miaoshaUser) {
        return "model_attribute";
    }

    @ModelAttribute
    public String baseTest() {
        return "1";
    }

    /**
     * @ModelAttribute不指定value或者name，那么返回的model中的key是方法返回类型的字符串(首字母小写)
     * value肯定是方法返回值
     * @return
     */
    @RequestMapping("/test2")
    public String test2() {
        return "model_attribute";
    }

    /**
     * @ModelAttribute和@RequestMapping同时存在于一个方法中，那么方法返回的将不是一个视图，而是model。
     * 那么请求响应的视图应该是与url所匹配的视图，如果url是modelAttribute/test3,那么返回的视图就是modelAttribute包下面的test3
     * 这样很容易报错，找不到视图。
     * @return
     */
    @RequestMapping("/test3")
    @ModelAttribute(value = "test2")
    public String test4() {
        return "model_attribute";
    }
}
