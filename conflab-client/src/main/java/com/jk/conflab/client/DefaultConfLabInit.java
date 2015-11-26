package com.jk.conflab.client;


import com.jk.conflab.client.utils.ConfConstants;
import com.jk.conflab.client.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Administrator on 2015/11/25.
 */
public abstract class DefaultConfLabInit {
    Logger logger = LoggerFactory.getLogger(DefaultConfLabInit.class);
    public static Boolean isDev=true;
    public DefaultConfLabInit() {
        if (isDev) {
            logger.info( "is dev");
            Set<Object> devAppIds = getDevAppIds();
        }else{
            logger.info( "not dev,appId:{}" + getAppId());
            //注册当前应用  。
            ConfLab.register(getAppId());
        }
    }

    private Set<Object> getDevAppIds() {
        String config_home = System.getenv("CONFIG_HOME");
        if (!StringUtils.hasText(config_home)) {
            config_home = System.getProperty("user.home");
            System.err.println("DEV MODEL Read EVN VAR 'CONFIG_HOME' IS NULL,USE HOME " + config_home);
        }
        String path = config_home + ConfConstants.DevConfigFileName;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            logger.error("开发环境，读取配置id文件错误。" + e.getMessage(), e);
            e.printStackTrace();
        }
        return properties.keySet();
    }

    protected abstract String getAppId();
}
