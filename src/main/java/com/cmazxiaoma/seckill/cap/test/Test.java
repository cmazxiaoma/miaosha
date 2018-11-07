package com.cmazxiaoma.seckill.cap.test;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/11/6 22:34
 */
public class Test {

    public static void main(String[] args) {
        People people = new People();
        people.wait("cmazxiaoma", new Callback() {
            @Override
            public void handle(String message) {
                System.out.println(message);
            }
        });
    }
}
