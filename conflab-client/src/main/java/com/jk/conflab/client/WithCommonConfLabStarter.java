package com.jk.conflab.client;


import com.jk.conflab.ConfConstants;
import com.jk.conflab.utils.StringUtils;
import org.I0Itec.zkclient.IZkDataListener;

import java.util.Properties;

/**
 * 含有通用配置的初始化对象。所有的客户端都会监听通用配置，通常通用配置只需要一组。
 * Created by Administrator on 2015/11/25.
 */
public abstract class WithCommonConfLabStarter extends DefaultConfLabStarter {
    //通用配置AppId
    private final String COMMON_CONFIG_ID = ConfConstants.COMMON;

    public WithCommonConfLabStarter() {
        super();
        //注册通用配置
        if (StringUtils.hasText(COMMON_CONFIG_ID)) {
            ConfLab.register(COMMON_CONFIG_ID, getListener());
        } else {
            logger.info("can not found prod common id,skip.");
        }

    }

    protected abstract String getAppId();

    protected abstract IZkDataListener getListener();
}
