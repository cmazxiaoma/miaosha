package com.cmazxiaoma.seckill.springmvc;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.aspectj.bridge.IMessage;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.method.ControllerAdviceBean;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewRequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/6/7 14:29
 */
public class Supports {

    /**
     * isAssignableFrom
     * Class1.isAssignableFrom(Class2)
     *
     * 1.Class1是否和Class2一样
     * 2.Class1是否是Class2的接口或者是父类
     *
     *
     * instance of
     * 判断一个对象是否是一个类、接口的实例或者是子类、子接口的实例
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(AbstractJackson2HttpMessageConverter.class
                .isAssignableFrom(FastJsonHttpMessageConverter.class));
        System.out.println(IMessage1.class.isAssignableFrom(Message1Impl.class));
        System.out.println(IMessage1.class.isAssignableFrom(IMessage1.class));
        System.out.println(BaseMessage.class.isAssignableFrom(Message1Impl.class));
        System.out.println(BaseMessage.class.isAssignableFrom(Message2Impl.class));

    }
}
