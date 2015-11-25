package com.jk.conflab.client;

import com.jk.conflab.client.utils.ConfConstants;
import com.jk.conflab.client.utils.ZkUtils;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jacky.cheng on 2015/11/23.
 */
public class ConfLab {
    private static Logger logger = LoggerFactory.getLogger(ConfLab.class);
    private static ZkClient zkClient = zkClient();
    private static String zkConfigRoot = ConfConstants.ZkConfigRoot;
    private static Map<String, String> configMap = new HashMap<String, String>();



    public static String getString(String key) {
        return getObject(key);
    }

    public static Integer getInteger(String key) {
        return Integer.valueOf(getObject(key));
    }

    public static Boolean getBoolean(String key) {
        return Boolean.valueOf(getObject(key));
    }

    public static void register(String... apps) {

        List<String> children = zkClient.getChildren(zkConfigRoot);
        for (String app : apps) {
            if (children.contains(app)) {
                Map<String, String> appMap = zkClient.readData(zkConfigRoot + app);
                configMap.putAll(appMap);
            } else {
                logger.error("Zookeeper path is null:{}", zkConfigRoot + app);
            }
            addConfigChangeEvent(app, new DefaultConfListenerAdapter());
        }
    }

    public static void addConfigChangeEvent(String appid, IZkDataListener listener) {
        zkClient.subscribeDataChanges(zkConfigRoot + "/" + appid, listener);
    }

    /**
     * 顺序取值：系统变量，环境变量，配置
     *
     * @param key
     * @return
     */
    private static String getObject(String key) {
        String value = System.getProperty(key);
        if (value != null) {
            return value;
        }
        value = System.getenv(key);
        if (value != null) {
            return value;
        }
        value = configMap.get(key);
        return value;
    }

    private static ZkClient zkClient(){
        ZkClient client = new ZkClient(getZkAddress(), 30000);
        client.setZkSerializer(new ZkUtils.StringSerializer("UTF-8"));
        return client;
    }

    private static String getZkAddress(){
        String zkAddress = System.getenv("ZK_ADDRESS");
        if (!StringUtils.hasText(zkAddress)) {
            logger.error("没有找到环境变量：ZK_ADDRESS，请配置后重试。");
            System.exit(-1);
            return null;
        }
        return zkAddress;
    }
}
