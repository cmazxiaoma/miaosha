package com.cmazxiaoma.seckill.matcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/7/1 14:09
 */
public class MapTest {

    public static void main(String[] args) {
        getMap();
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "init");
        System.out.println("init");

        try {
            map.put("key", "try");
            System.out.println("try");
        } catch (Exception e) {
            map.put("key", "catch");
            System.out.println("catch");
        } finally {
            map.put("key", "finally");
            System.out.println("finally");
            map = null;
        }

        return map;
    }
}
