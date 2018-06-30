package com.cmazxiaoma.seckill.matcher;

import org.apache.commons.lang3.ClassUtils;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/6/21 14:18
 */
public class ClassUtilsTest {

    public static void main(String[] args) {
        System.out.println("shortClassName:" + ClassUtils.getShortClassName(ClassUtilsTest.class));
        System.out.println("name:" + ClassUtilsTest.class.getName());
        System.out.println("simpleName:" + ClassUtilsTest.class.getSimpleName());
        System.out.println("canonicalName:" + ClassUtilsTest.class.getCanonicalName());
        System.out.println("typeName:" + ClassUtilsTest.class.getTypeName());

        System.out.println("shortClassName:" + ClassUtils.getShortClassName(String.class));
        System.out.println("name:" + String.class.getName());
        System.out.println("simpleName:" + String.class.getSimpleName());
        System.out.println("canonicalName:" + String.class.getCanonicalName());
        System.out.println("typeName:" + String.class.getTypeName());

        String[] test = new String[5];
        System.out.println("shortClassName:" + ClassUtils.getShortClassName(test.getClass()));
        System.out.println("name:" + test.getClass().getName());
        System.out.println("simpleName:" + test.getClass().getSimpleName());
        System.out.println("canonicalName:" + test.getClass().getCanonicalName());
        System.out.println("typeName:" + test.getClass().getTypeName());

        System.out.println(Object.class.isInstance(test));

        System.out.println(org.springframework.util.ClassUtils.getShortNameAsProperty(ClassUtilsTest.class));
    }
}
