package com.cmazxiaoma.seckill.matcher;

import com.google.common.collect.Maps;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/6/21 15:59
 */
public class KnowAttributeNamesTest {

    public static void main(String[] args) {
        Map<String, Boolean> map = new ConcurrentHashMap<>(4);
        map.put("1", true);
        map.put("2", true);
        map.put("3", true);
        map.put("4", true);

        Set<String> set = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>(4));
        set.add("1");
        set.add("2");
        set.add("3");
        set.add("4");
        set.add("5");
        System.out.println(set.size());
    }
}
