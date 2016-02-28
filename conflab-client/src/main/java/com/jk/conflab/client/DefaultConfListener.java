package com.jk.conflab.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultConfListener implements IConfigListener {
    Logger logger = LoggerFactory.getLogger(getClass());
    private String appId;

    public DefaultConfListener(String appId) {
        this.appId = appId;
    }

    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        ConfLab.update(getAppId());
        logger.info("app:{},config data has bean changed! reload success.", appId);
    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        ConfLab.delete();
        logger.info("app:{},config data has bean deleted", appId);
    }

    @Override
    public String getAppId() {
        return appId;
    }
}