package com.cmazxiaoma.seckill.matcher;

import org.springframework.util.AntPathMatcher;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/6/12 6:39
 */
public class AntPathMatcherTest {

    public static void main(String[] args) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        /**
         * 是否是路径匹配模式
         */
        System.out.println(antPathMatcher.isPattern("/login_tologin"));
    }
}
