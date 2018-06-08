package com.cmazxiaoma.seckill.redistest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/10 16:29
 */
public class RedisKeyTest1 {

    public static void main(String[] args) {
        List<String> dataList = new ArrayList<>();
        dataList.add("1");
        dataList.add("2");
        dataList.add("3");
        dataList.add("4");
        dataList.add("5");

        String[] array2 = dataList.toArray(new String[0]);
        System.out.println(array2);

        for (String str : array2) {
            System.out.println(str);
        }

        String[] array3 = dataList.toArray(new String[0]);
        System.out.println(array3);

        for (String str : array3) {
            System.out.println(str);
        }
    }
}
