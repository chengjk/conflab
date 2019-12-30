package com.jk.conflab;

import com.alibaba.fastjson.JSON;
import com.jk.conflab.model.App;
import com.jk.conflab.model.ConfGroup;
import com.jk.conflab.model.Config;
import com.jk.conflab.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/9/25.
 */
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {
    Logger logger = Logger.getLogger(getClass());

    @Value("${zookeeper.server}")
    private String zkServer;

    @Bean
    public String test() {
        return "StrBean";
    }

    @Bean
    public String zkRootPath() {
        return "/conflab/config";
    }

    @Bean
    public ZkClient zkClient(String getZkAddress) {
        ZkClient client = new ZkClient(getZkAddress, 3000);
        client.setZkSerializer(new ZkUtils.StringSerializer("UTF-8"));
        return client;
    }


    @Bean
    public String getZkAddress() {
        String sysEnv = System.getenv("ZK_ADDRESS");
        if (StringUtils.hasText(sysEnv)) {
            return sysEnv;
        }
        logger.warn("没有找到环境变量：ZK_ADDRESS，尝试配置文件。");
        if (StringUtils.hasText(zkServer)) {
            return zkServer;
        }
        logger.error("没找到有效配置：zookeeper.server。");
        return null;
    }
}
