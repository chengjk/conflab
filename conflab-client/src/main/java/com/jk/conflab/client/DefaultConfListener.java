package com.jk.conflab.client;

import com.jk.conflab.client.utils.ConfConstants;
import org.I0Itec.zkclient.IZkDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultConfListener implements IZkDataListener {
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 更新回调
     * @param dataPath
     * @param data
     * @throws Exception
     */
    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        String appId= dataPath.substring(ConfConstants.ZK_CONFIG_ROOT.length()+1);
        ConfLab.update(appId);
        logger.info("app:{},config data has bean changed! reload success.", appId);
    }

    /**
     * 删除回调
     * @param dataPath
     * @throws Exception
     */
    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        ConfLab.delete();
        String appId = dataPath.substring(ConfConstants.ZK_CONFIG_ROOT.length()+1);
        logger.info("app:{},config data has bean deleted", appId);
    }
}