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
    public MyConfLabInit myConfLabInit() {
        MyConfLabInit.isDev = false;
        return new MyConfLabInit();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
