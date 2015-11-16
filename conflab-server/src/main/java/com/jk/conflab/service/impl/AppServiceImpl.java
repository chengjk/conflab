package com.jk.conflab.service.impl;

import com.alibaba.fastjson.JSON;
import com.jk.conflab.model.App;
import com.jk.conflab.model.ConfGroup;
import com.jk.conflab.model.Config;
import com.jk.conflab.repository.AppRepository;
import com.jk.conflab.repository.ConfGroupRepository;
import com.jk.conflab.repository.ConfigRepository;
import com.jk.conflab.service.AppService;
import com.jk.conflab.service.ZkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jacky.cheng on 2015/11/10.
 */
@Service
public class AppServiceImpl implements AppService {
    @Autowired
    AppRepository appRepository;
    @Autowired
    ConfGroupRepository confGroupRepository;
    @Autowired
    ConfigRepository configRepository;

    @Autowired
    ZkService zkService;

    @Override
    public Iterable<App> findAll() {
        return appRepository.findAll();
    }


    @Override
    public App copy(Long srcId, String tarName) {
        App src = appRepository.findOne(srcId);
        src.setId(null);
        src.setName(tarName);
        App tar = appRepository.save(src);
        List<ConfGroup> groups = confGroupRepository.findByAppId(srcId);
        for (ConfGroup group : groups) {
            group.setId(null);
            group.setAppId(tar.getId());
            List<Config> configs = configRepository.findByGroupId(group.getId());
            for (Config config : configs) {
                config.setId(null);
                config.setAppId(tar.getId());
                config.setGroupId(group.getId());
            }
            configRepository.save(configs);
        }
        confGroupRepository.save(groups);
        return tar;
    }

    @Override
    public boolean del(Long id) {
        appRepository.delete(id);
        confGroupRepository.deleteByAppId(id);
        confGroupRepository.deleteByAppId(id);
        return false;
    }

    @Override
    public boolean push(Long appId, String appName) {
        Map<String, String> publish = new HashMap<String, String>();
        List<Config> configs = configRepository.findByAppId(appId);
        for (Config config : configs) {
            publish.put(config.getKey(), config.getValue());
        }
        zkService.publish(appName, JSON.toJSONString(publish));
        return false;
    }
}
