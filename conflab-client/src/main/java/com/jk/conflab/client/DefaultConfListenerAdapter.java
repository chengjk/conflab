package com.jk.conflab.client;

import com.jk.conflab.client.utils.ConfConstants;
import org.I0Itec.zkclient.IZkDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultConfListenerAdapter implements IZkDataListener {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        String appId = getAppFromPath(dataPath);
        ConfLab.register(appId);
        logger.info("app:{},config data has bean changed! reload success.", appId);
    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        String appId = getAppFromPath(dataPath);
        logger.info("app:{},config data has bean deleted", appId);
    }

    private String getAppFromPath(String dataPath) {
        String root = ConfConstants.ZkConfigRoot;
        return dataPath.substring(root.length());
    }
}