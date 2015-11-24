package com.jk.conflab.client;

import com.jk.conflab.client.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jacky.cheng on 2015/11/23.
 */
@Component
public class ConfigLab {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ZkClient zkClient = zkClient();
    private String zkConfigRoot = "conflab/config/";
    private Map<String, String> configMap = new HashMap<String, String>();

    public String getString(String key) {
        return configMap.get(key);
    }

    public Integer getInteger(String key) {
        return Integer.valueOf(configMap.get(key));
    }

    public Boolean getBoolean(String key) {
        return Boolean.valueOf(configMap.get(key));
    }


    public void register(String app) {
        List<String> children = zkClient.getChildren(zkConfigRoot);
        if (children.contains(app)) {
            configMap = zkClient.readData(zkConfigRoot + app);
        } else {
            logger.error("Zookeeper path is null:{}", zkConfigRoot + app);
        }
    }

    public void register(String... apps) {
        List<String> children = zkClient.getChildren(zkConfigRoot);
        for (String app : apps) {
            if (children.contains(app)) {
                Map<String, String> appMap = zkClient.readData(zkConfigRoot + app);
                configMap.putAll(appMap);
            } else {
                logger.error("Zookeeper path is null:{}", zkConfigRoot + app);
            }
        }
    }


    private ZkClient zkClient() {
        ZkClient client = new ZkClient(getZkAddress(), 30000);
        client.setZkSerializer(new ZkUtils.StringSerializer("UTF-8"));
        return client;
    }

    private String getZkAddress() {
        String zkAddress = System.getenv("ZK_ADDRESS");
        if (!StringUtils.hasText(zkAddress)) {
            logger.error("没有找到环境变量：ZK_ADDRESS，请配置后重试。");
            return null;
        }
        return zkAddress;
    }
}
