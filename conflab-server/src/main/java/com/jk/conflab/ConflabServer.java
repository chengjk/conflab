package com.jk.conflab;


import org.springframework.boot.SpringApplication;

/**
 * Runner
 * Created by jacky.cheng on 2015/9/25.
 */
public class ConflabServer {
    private ConflabServer() {
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
