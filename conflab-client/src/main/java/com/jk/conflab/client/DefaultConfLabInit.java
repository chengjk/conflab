package com.jk.conflab.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2015/11/25.
 */
public abstract class DefaultConfLabInit {
    Logger logger = LoggerFactory.getLogger(DefaultConfLabInit.class);
    public static Boolean isDev=true;
    public DefaultConfLabInit() {
        if (isDev) {
            //// TODO: 2015/11/25  从环境中取得配置应用id，注册。可以是多个。
            logger.info( "is dev");
        }else{
            logger.info( "not dev,appId:{}" + getAppId());
            //注册当前应用  。
            ConfLab.register(getAppId());
        }
    }
    protected abstract String getAppId();
}
