package com.jk.conflab.client;


import org.I0Itec.zkclient.IZkDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认初始化对象
 * Created by Administrator on 2015/11/25.
 */
public abstract class DefaultConfLabStarter {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public DefaultConfLabStarter() {
        ConfLab.init(getZookeeper());
        //注册当前应用
        ConfLab.register(getAppId(), getListener());

    }
    protected abstract String getZookeeper();

    /**
     * 应用id
     *
     * @return
     */
    protected abstract String getAppId();

    /**
     * 监听器
     *
     * @return
     */
    protected abstract IZkDataListener getListener();
}
