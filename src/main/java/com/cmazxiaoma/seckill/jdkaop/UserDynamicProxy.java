package com.cmazxiaoma.seckill.jdkaop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/6/1 17:52
 */
public class UserDynamicProxy implements InvocationHandler {

    private Object target;

    public UserDynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //proxy是代理类
        System.out.println("login before");
        Object result = method.invoke(target, args);
        System.out.println("login after");
        return result;
    }
}
