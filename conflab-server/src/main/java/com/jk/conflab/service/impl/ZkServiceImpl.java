package com.jk.conflab.service.impl;

import com.alibaba.fastjson.JSON;
import com.jk.conflab.model.App;
import com.jk.conflab.service.ZkService;
import com.jk.conflab.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/16.
 */
@Service
public class ZkServiceImpl implements ZkService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ZkClient client;
    @Autowired
    private String zkRootPath;

    @Override
    public boolean publish(String app, String configData) {
        try {
            String path = ZkUtils.getPath(zkRootPath, app);
            ZkUtils.mkPath(client, path);
            if (!client.exists(path)) {
                client.createPersistent(path);
                client.writeData(path, configData);
                logger.info("成功发布配置，{}", app);
            } else {
                client.writeData(path, configData);
                logger.info("成功更新配置，{}", app);
            }
        } catch (RuntimeException e) {
            logger.error("发布配置失败：appId", e);
            return false;
        }
        return true;
    }


    @Override
    public boolean delete(String app) {
        String path = ZkUtils.getPath(zkRootPath, app);
        boolean flag = false;
        if (client.exists(path)) {
            client.delete(path);
            logger.info("delete path success!");
            flag = true;
        } else {
            logger.info("path is not exist,can not delete.");
        }
        return flag;
    }

    @Override
    public List<App> readAll() {

        List<App> result = new ArrayList<>();
        if (client.exists(zkRootPath)) {
            List<String> allPath = client.getChildren(zkRootPath);
            if (!CollectionUtils.isEmpty(allPath)) {
                for (String p : allPath) {
                    String appStr = client.readData(ZkUtils.getPath(zkRootPath, p));
                    App app = JSON.parseObject(appStr, App.class);
                    if (app != null) {
                        result.add(app);
                    }
                }
            }
        } else {
            logger.info("path is not exist,can not delete.");
        }
        return result;
    }
}
