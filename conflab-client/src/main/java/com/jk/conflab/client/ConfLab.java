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
    private static String zkConfigRoot = ConfConstants.ZK_CONFIG_ROOT;
    // app ,key:value
    private static Map<String, Map<String, String>> appMap = new HashMap<String, Map<String, String>>();

    private ConfLab() { }

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
     * 注册app。
     * @param app
     * @param listener
     */
    public static void register(String app,IZkDataListener listener) {
        update(app);
        addConfigChangeEvent(app, listener);
    }

    /**
     * 更新配置
     * @param app
     */
    public static void update(String app) {
        ConfLab.appMap.remove(app);// clear
        List<String> children = zkClient.getChildren(zkConfigRoot);
        if (children.contains(app)) {
            String dataStr = zkClient.readData(zkConfigRoot + "/" + app);
            Map<String, String> appMap = JSON.parseObject(dataStr, new TypeReference<Map<String, String>>() {
            });
            ConfLab.appMap.put(app,appMap);
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
        String zkAddress = System.getenv(ConfConstants.DEV_ZK_PATHNAME);
        if (!StringUtils.hasText(zkAddress)) {
            logger.error("没有找到环境变量：{}，请配置后重试。",ConfConstants.DEV_ZK_PATHNAME);
            System.exit(-1);
            return null;
        }
        return zkAddress;
    }
}
