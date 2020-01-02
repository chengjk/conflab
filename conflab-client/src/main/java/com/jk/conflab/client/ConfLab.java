package com.jk.conflab.client;

import com.alibaba.fastjson.JSON;
import com.jk.conflab.ConfConstants;
import com.jk.conflab.model.App;
import com.jk.conflab.utils.StringUtils;
import com.jk.conflab.utils.ZkUtils;
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
    private static ZkClient zkClient;
    private static String zkConfigRoot = ConfConstants.ZK_CONFIG_ROOT;
    // app ,key:value
    private static Map<String, Map<String, String>> appMap = new HashMap<>();
    private static String zkHost;

    private ConfLab() {
    }

    public static void init(String zkHost) {
        ConfLab.zkHost = zkHost;
        zkClient = zkClient();
    }

    /**
     * 获取一个String值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(String key,String defaultValue) {
        String data = getObject(key);
        return data != null ? data : defaultValue;
    }

    public static String getString(String key) {
        return getString(key, null);
    }

    /**
     * 获取一个Int值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static Integer getInteger(String key,Integer defaultValue) {
        String data = getObject(key);
        return data != null ? Integer.valueOf(data) : defaultValue;
    }

    public static Integer getInteger(String key){
        return getInteger(key, null);
    }

    /**
     * 获取一个Boolean值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(String key,Boolean defaultValue) {
        String data = getObject(key);
        return data != null ? Boolean.valueOf(data) : defaultValue;
    }


    /**
     * 获取Long值
     * @param key
     * @param defaultValue
     * @return
     */
    public static Long getLong(String key,Long defaultValue) {
        String data = getObject(key);
        if (data != null) {
            return Long.valueOf(data);
        }else {
            return defaultValue;
        }
    }

    public static Long getLong(String key){
        return getLong(key, null);
    }

    /**
     * 注册app。
     *
     * @param app
     * @param listener
     */
    public static void register(String app, IZkDataListener listener) {
        if (zkClient != null) {
            update(app);
            addConfigChangeEvent(app, listener);
        } else {
            logger.error("请先init");
        }
    }

    /**
     * 更新配置
     *
     * @param app
     */
    public static void update(String app) {
        ConfLab.appMap.remove(app);// clear
        List<String> children = zkClient.getChildren(zkConfigRoot);
        if (children.contains(app)) {
            String dataStr = zkClient.readData(zkConfigRoot + "/" + app);
            App fetched = JSON.parseObject(dataStr, App.class);
            ConfLab.appMap.put(app, fetched.toMap());
            logger.info("update success, appId:{};{}", app, JSON.toJSONString(appMap));
        } else {
            logger.error(" update failed ,ZK path is null:{}", zkConfigRoot + "/" + app);
        }
    }

    /**
     * 删除配置
     */
    public static void delete(String app) {
        appMap.remove(app);
        logger.info("delete config! appId:{}", app);
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
        //jvm环境变量
        String value = System.getProperty(key);
        if (value != null) {
            return value;
        }
        //系统环境变量
        value = System.getenv(key);
        if (value != null) {
            return value;
        }
        //配置中心
        for (Map<String, String> configMap : appMap.values()) {
            value = configMap.get(key);
            if (value != null) break;
        }
        if (value == null) {
            logger.warn("没有找到配置，key：{}", key);
        }
        return value;
    }

    /**
     * 初始化zk客户端
     *
     * @return
     */
    private static ZkClient zkClient() {
        ZkClient client = new ZkClient(getZkAddress(), 3000);
        client.setZkSerializer(new ZkUtils.StringSerializer("UTF-8"));
        return client;
    }

    /**
     * 优先使用环境变量配置的zk地址  host:port
     *
     * @return
     */
    private static String getZkAddress() {
        String zkAddress = System.getenv(ConfConstants.ZK_ENV_VAR);
        if (StringUtils.hasText(zkAddress)) {
            logger.info("找到可用环境变量：{}", ConfConstants.ZK_ENV_VAR);
            return zkAddress;
        }
        logger.warn("没有找到环境变量：{}，尝试注入变量。", ConfConstants.ZK_ENV_VAR);
        if (StringUtils.hasText(zkHost)) {
            logger.info("找到可用变量，zk server:{}", zkHost);
            return zkHost;
        }
        logger.error("没有找到可用的zookeeper配置,请配置后重试。");
        System.exit(-1);
        return null;
    }
}
