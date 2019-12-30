package com.jk.conflab.client;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MyConfLabInit extends DefaultConfLabStarter {
    public static String appId;

    @Override
    protected String getAppId() {
        return appId;
    }

    @Override
    protected String getZookeeper() {
        return "172.30.30.17:2181";
    }

    @Override
    protected DefaultConfListener getListener() {
        return new DefaultConfListener();
    }

}
