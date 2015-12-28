package com.jk.conflab.demo;

import com.jk.conflab.client.ConfLab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by Administrator on 2015/11/25.
 */
@SpringBootApplication
public class Application {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public void myConfLabInit() {
        MyConfLabInit.isDev = false;
        new MyConfLabInit();
    }

    @Bean
    public void getConfigData() {
        //测试
        System.setProperty("conflab", "conflab_value");
        String value = ConfLab.getString("conflab");
        if ("conflab_value".equals(value)) {
            logger.info("get data from system properties success!");
        }else {
            logger.error("get data from system properties failed!");
        }
        String test = ConfLab.getString("test");
        if ("test_value".equals(test)) {
            logger.info("get data from zookeeper success!");
        }else {
            logger.error("get data from zookeeper failed!");
        }
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}