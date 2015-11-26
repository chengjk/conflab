package com.jk.conflab.client;

import org.I0Itec.zkclient.IZkDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultConfListenerAdapter implements IZkDataListener {
    Logger logger = LoggerFactory.getLogger(getClass());
    private String appId;

    public DefaultConfListenerAdapter(String appId) {
        this.appId = appId;
    }

    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        ConfLab.register(appId);
        logger.info("app:{},config data has bean changed! reload success.", appId);
    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        logger.info("app:{},config data has bean deleted", appId);
    }
}