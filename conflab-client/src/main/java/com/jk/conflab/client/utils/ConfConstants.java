package com.jk.conflab.client.utils;

/**
 * Created by Administrator on 2015/11/25.
 */
public class ConfConstants {
    private ConfConstants() {
    }

    /**
     * 配置存放的zookeeper跟目录
     */
    public static final String ZK_CONFIG_ROOT = "/conflab/config";
    /**
     * 开发环境下，appIds保存文件
     */
    public static final String DEV_CONFIG_FILE_NAME = "conflab.properties";
    /**
     * 通用配置AppId
     */
    public static final String COMMON_CONFIG_ID = "common";

}
