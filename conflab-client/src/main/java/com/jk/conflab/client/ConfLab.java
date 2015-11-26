package com.jk.conflab.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jk.conflab.client.utils.ConfConstants;
import com.jk.conflab.client.utils.StringUtils;
import com.jk.conflab.client.utils.ZkUtils;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    /**
     * 获取一个String值
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return getObject(key);
    }

    /**
     * 获取一个Int值
     * @param key
     * @return
     */
    public static Integer getInteger(String key) {
        String data = getObject(key);
        if (data != null) {
            return Integer.valueOf(data);
        }
        return null;
    }

    /**
     * 获取一个Boolean值
     * @param key
     * @return
     */
    public static Boolean getBoolean(String key) {
        String data = getObject(key);
        if (data != null) {
            return Boolean.valueOf(data);
        }
        return null;
    }

    /**
     * 注册一个或多个app。
     * @param apps
     */
    public static void register(String... apps) {
        List<String> children = zkClient.getChildren(zkConfigRoot);
        for (String app : apps) {
            if (children.contains(app)) {
                String dataStr = zkClient.readData(zkConfigRoot + "/" + app);
                Map<String, String> appMap = JSON.parseObject(dataStr, new TypeReference<Map<String, String>>() {
                });
                configMap.putAll(appMap);
            } else {
                logger.error("Zookeeper path is null:{}", zkConfigRoot + "/" + app);
            }
            addConfigChangeEvent(app, new DefaultConfListenerAdapter());
        }
    }

    /**
     * 增加更新监控
     *
     * @param appId
     * @param listener
     */
    public static void addConfigChangeEvent(String appId, IZkDataListener listener) {
        zkClient.subscribeDataChanges(zkConfigRoot + "/" + appId, listener);
    }

    /**
     * 顺序取值：系统变量，环境变量，配置
     *
     * @param key
     * @return
     */
    private static String getObject(String key) {
        if (key == null) {
            return null;
        }
        String value = System.getProperty(key);
        if (value != null) {
            return value;
        }
        value = System.getenv(key);
        if (value != null) {
            return value;
        }
        value = configMap.get(key);
        if (value == null) {
            logger.warn("没有找到配置，key：{}", key);
        }
        return value;
    }

    /**
     * 初始化zk客户端
     * @return
     */
    private static ZkClient zkClient(){
        ZkClient client = new ZkClient(getZkAddress(), 30000);
        client.setZkSerializer(new ZkUtils.StringSerializer("UTF-8"));
        return client;
    }

    /**
     * 环境变量中获取含端口的zk地址
     * @return
     */
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
