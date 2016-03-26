package com.jk.conflab.client;


import com.jk.conflab.client.utils.StringUtils;
import org.I0Itec.zkclient.IZkDataListener;

import java.util.Properties;

/**
 * 含有通用配置的初始化对象。所有的客户端都会监听通用配置，通常通用配置只需要一组。
 * Created by Administrator on 2015/11/25.
 */
public abstract class WithCommonConfLabInit extends DefaultConfLabInit {
    //通用配置AppId
    private String COMMON_CONFIG_ID="common";
    public WithCommonConfLabInit() {
        super();
        if (isDev) {
            logger.info( "is dev, appId:{}",getAppId());
            Properties properties = getDevSetting();
            //注册通用配置
            String devCommonId = properties.getProperty(COMMON_CONFIG_ID);
            if (StringUtils.hasText(devCommonId)) {
                ConfLab.register(devCommonId, getListener());
            }else {
                logger.info("can not found dev common id,skip.");
            }
        }else{
            logger.info( "not dev,appId:{}", getAppId());
            //注册通用配置
            if (StringUtils.hasText(COMMON_CONFIG_ID)) {
                ConfLab.register(COMMON_CONFIG_ID,getListener());
            }else {
                logger.info("can not found prod common id,skip.");
            }
        }
    }



    protected abstract String getAppId();
    protected abstract IZkDataListener getListener();
}
