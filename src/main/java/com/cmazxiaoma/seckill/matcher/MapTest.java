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
        Map<String, String> map = getMap();
        System.out.println(map.get("key"));
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "init");
        System.out.println("init");

        try {
            map.put("key", "try");
            System.out.println("try");
            Integer integer = Integer.parseInt(null);
            return map;
        } catch (Exception e) {
            map.put("key", "catch");
            System.out.println("catch");
            return map;
        } finally {
            map.put("key", "finally");
            System.out.println("finally");
            map = null;
        }
    }
}
