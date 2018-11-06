package com.cmazxiaoma.seckill.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.cmazxiaoma.seckill.access.AccessInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 15:13
 */
//@Configuration
//@Slf4j
//public class WebMvcConfig extends WebMvcConfigurerAdapter {
//
//    @Autowired
//    private UserArgumentResolver userArgumentResolver;
//
//    @Autowired
//    private AccessInterceptor accessInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(accessInterceptor);
//    }
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(userArgumentResolver);
//    }
//
//    /**
//     * 让静态资源放行
//     * @param registry
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        super.addResourceHandlers(registry);
//    }
//
//    //使用阿里 FastJson 作为JSON MessageConverter
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//
//        log.info("converters:" + converters.toString());
//        List<MediaType> supportMediaTypeList = new ArrayList<>();
////        supportMediaTypeList.add(MediaType.TEXT_HTML);
//          supportMediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
////        supportMediaTypeList.add(MediaType.IMAGE_GIF);
////        supportMediaTypeList.add(MediaType.IMAGE_JPEG);
////        supportMediaTypeList.add(MediaType.IMAGE_PNG);
//
//        FastJsonConfig config = new FastJsonConfig();
////        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        config.setSerializerFeatures(
//                SerializerFeature.WriteMapNullValue,//保留空的字段
//                SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
//                SerializerFeature.WriteNullNumberAsZero,//Number null -> 0
//                SerializerFeature.WriteNullListAsEmpty,//List null-> []
//                SerializerFeature.WriteNullBooleanAsFalse);//Boolean null -> false
//        converter.setFastJsonConfig(config);
//        converter.setSupportedMediaTypes(supportMediaTypeList);
//        converter.setDefaultCharset(Charset.forName("UTF-8"));
//        converters.add(converter);
//    }
//
//}
