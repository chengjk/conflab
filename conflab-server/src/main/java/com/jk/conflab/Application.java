package com.jk.conflab;

import com.jk.conflab.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by jacky.cheng on 2015/9/25.
 */
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {
    Logger logger = Logger.getLogger(getClass());

    @Bean
    public String test() {
        return "StrBean";
    }

    @Bean
    public ZkClient zkClient(String getZkAddress) {
        ZkClient client = new ZkClient(getZkAddress, 30000);
        client.setZkSerializer(new ZkUtils.StringSerializer("UTF-8"));
        return client;
    }


    @Bean
    public String getZkAddress() {
        String zkAddress = System.getenv("ZK_ADDRESS");
        if (!StringUtils.hasText(zkAddress)) {
            logger.error("没有找到环境变量：ZK_ADDRESS，请配置后重试。");
            return null;
        }
        return zkAddress;
    }
}
