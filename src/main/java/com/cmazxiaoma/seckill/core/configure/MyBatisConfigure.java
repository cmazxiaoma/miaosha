package com.cmazxiaoma.seckill.core.configure;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

import static com.cmazxiaoma.seckill.core.ProjectConstant.MAPPER_INTERFACE_REFERENCE;
import static com.cmazxiaoma.seckill.core.ProjectConstant.MAPPER_PACKAGE;
import static com.cmazxiaoma.seckill.core.ProjectConstant.MODEL_PACKAGE;


/**
 * Mybatis & Mapper & PageHelper 配置
 */
@Configuration
public class MyBatisConfigure {

    @Resource
    private DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage(MODEL_PACKAGE);

        //配置分页插件,详情请查阅官方文档
        //PageHelper5.1.2版本, PageInterceptor取代了PageHelper
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("pageSizeZero", "true");//分页尺寸为0时查询所有纪录不再执行分页
        properties.setProperty("reasonable", "true");//页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setProperty("supportMethodsArguments", "true");//支持通过Mapper接口参数来传递分页参数
        pageInterceptor.setProperties(properties);

        //添加插件
        factory.setPlugins(new Interceptor[]{pageInterceptor});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return factory.getObject();
    }

    @Configuration
    @AutoConfigureAfter(MyBatisConfigure.class)
    public static class MyBatisMapperScannerConfigure {

        @Bean
        public static MapperScannerConfigurer mapperScannerConfigurer() {
            MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
            mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
            mapperScannerConfigurer.setBasePackage(MAPPER_PACKAGE);

            //配置通用Mapper，详情请查阅官方文档
            Properties properties = new Properties();
            properties.setProperty("mappers", MAPPER_INTERFACE_REFERENCE);
            //insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
            properties.setProperty("notEmpty", "true");
            properties.setProperty("IDENTITY", "MYSQL");
            mapperScannerConfigurer.setProperties(properties);

            return mapperScannerConfigurer;
        }
    }

}

