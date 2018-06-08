package com.cmazxiaoma.seckill.jdkaop;

import java.lang.reflect.Proxy;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/6/1 18:00
 */
public class DymaticProxyDemo {

    public static void main(String[] args) {
        IUserService userService = new UserServiceImpl();
        UserDynamicProxy handler = new UserDynamicProxy(userService);
        //interface com.cmazxiaoma.seckill.jdkaop.IUserService
        Class<?>[] classArray = userService.getClass().getInterfaces();
        IUserService userServiceProxy = (IUserService) Proxy.newProxyInstance(
                userService.getClass().getClassLoader(),
                userService.getClass().getInterfaces(),
                handler
        );
        userServiceProxy.login();
    }
}
