package com.cmazxiaoma.seckill.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {

    private static Properties props;

    static {
        try {
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("application-dev.properties");
            props = new Properties();
            props.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  spring.datasource.druid.url
        spring.datasource.druid.username
        spring.datasource.druid.password
        spring.datasource.druid.driver-class-name
     * @return
     * @throws Exception
     */

    public static Connection getConn() throws Exception {
        String url = props.getProperty("spring.datasource.druid.url");
        String username = props.getProperty("spring.datasource.druid.username");
        String password = props.getProperty("spring.datasource.druid.password");
        String driver = props.getProperty("spring.datasource.druid.driver-class-name");
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }
}
