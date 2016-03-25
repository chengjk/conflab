package com.jk.conflab.client;


import com.jk.conflab.client.utils.ConfConstants;
import com.jk.conflab.client.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2015/11/25.
 */
public abstract class DefaultConfLabInit {
    Logger logger = LoggerFactory.getLogger(DefaultConfLabInit.class);
    public static Boolean isDev=true;
    public DefaultConfLabInit() {
        if (isDev) {
            logger.info( "is dev, appId:{}",getAppId());
            Properties properties = getDevSetting();
            //注册开发环境配置
            String devAppId = properties.getProperty(getAppId());
            if (StringUtils.hasText(devAppId)) {
                ConfLab.register(devAppId, getListener());
            }else {
                logger.error("can not found dev app id :{}",getAppId());
            }
            //注册通用配置
            String devCommonId = properties.getProperty(ConfConstants.COMMON_CONFIG_ID);
            if (StringUtils.hasText(devAppId)) {
                ConfLab.register(devCommonId, getListener());
            }else {
                logger.info("can not found dev common id,skip.");
            }
        }else{
            logger.info( "not dev,appId:{}", getAppId());
            //注册当前应用
            ConfLab.register(getAppId(),getListener());
            //注册通用配置
            ConfLab.register(ConfConstants.COMMON_CONFIG_ID,getListener()); // FIXME: 2016/3/25  必须要有
        }
    }

    private Properties getDevSetting() {
        String config_home = System.getenv("CONFIG_HOME");
        if (!StringUtils.hasText(config_home)) {
            config_home = System.getProperty("user.home");
            logger.error("DEV MODEL Read EVN VAR 'CONFIG_HOME' IS NULL,USE HOME " + config_home);
            System.err.println("DEV MODEL Read EVN VAR 'CONFIG_HOME' IS NULL,USE HOME " + config_home);
        }
        String path = config_home +"/"+ ConfConstants.DEV_CONFIG_FILE_NAME;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            logger.error("开发环境，读取配置id文件错误。" + e.getMessage(), e);
            System.exit(0);
        }
        return properties;
    }

    protected abstract String getAppId();
    protected abstract DefaultConfListener getListener();
}
