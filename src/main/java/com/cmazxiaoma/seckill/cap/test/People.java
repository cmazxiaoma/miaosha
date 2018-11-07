package com.cmazxiaoma.seckill.cap.test;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/6 22:33
 */
public class People {

    public void wait(String name, Callback callback) {
        callback.handle(name);
    }
}
