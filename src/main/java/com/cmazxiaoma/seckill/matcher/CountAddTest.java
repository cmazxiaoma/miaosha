package com.cmazxiaoma.seckill.matcher;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/7/1 15:57
 */
public class CountAddTest {

    public static void main(String[] args) {
        test1();
        test2();
    }

    public static void test1() {
        int count = 0;
        count = ++count;
        System.out.println("======test1");
        System.out.println("count=" + count);
    }

    public static void test2() {
        int count = 0;
        count = count++;
        System.out.println("======test2");
        System.out.println("count=" + count);
    }
}
