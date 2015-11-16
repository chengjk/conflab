package com.jk.conflab.service.impl;

import com.jk.conflab.service.ZkService;
import com.jk.conflab.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jacky.cheng on 2015/11/16.
 */
@Service
public class ZkServiceImpl implements ZkService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ZkClient client;
    private String zkRootPath = "/test/config";

    @Override
    public boolean publish(String appId, String configData) {
        try {
            String path = ZkUtils.getPath(zkRootPath, appId);
            if (!client.exists(path)) {
                client.create(path, configData, CreateMode.PERSISTENT);
                logger.info("成功发布配置，{}", appId);
            } else {
                client.writeData(path, configData);
                logger.info("成功更新配置，{}", appId);
            }
        } catch (RuntimeException e) {
            logger.error("成功配置失败：appId", e);
            return false;
        }
        return true;
    }
}
