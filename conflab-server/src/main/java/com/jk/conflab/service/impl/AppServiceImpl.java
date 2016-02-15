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
        if (appName == null) {
            App app = appRepository.findOne(appId);
            appName = app.getName();
        }
        Map<String, String> publish = new HashMap<String, String>();
        List<Config> configs = configRepository.findByAppId(appId);
        for (Config config : configs) {
            publish.put(config.getKey(), config.getValue());
        }
        return zkService.publish(appName, JSON.toJSONString(publish));
    }

    @Override
    public boolean pushAll(String key) {
        Iterable<App> apps;
        if (key == null) {
            apps = appRepository.findAll();
        } else {
            apps = appRepository.findByNameLike(key);
        }
        for (App app : apps) {
            if (!push(app.getId(), app.getName())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public App exportOne(Long id) {
        App one = appRepository.findOne(id);
        one.setGroups(buildSascas(id));
        return one;
    }



    @Override
    public Iterable<App> exportByKey(String key) {
        Iterable<App> apps = appRepository.findByNameLike(key);
        for (App app : apps) {
            app.setGroups(buildSascas(app.getId()));
        }
        return apps;
    }


    private  List<ConfGroup> buildSascas(Long appId) {
        List<ConfGroup> groups = confGroupRepository.findByAppId(appId);
        for (ConfGroup group : groups) {
            List<Config> configs = configRepository.findByGroupId(group.getId());
            group.setConfigs(configs);
        }
        return groups;
    }

    @Override
    public boolean importApps(List<App> apps) {
        for (App app : apps) {
            importApp(app);
        }
        return true;
    }

    @Override
    public boolean importApp(App app) {
        app.setId(null);
        App rApp = appRepository.save(app);
        for (ConfGroup group : app.getGroups()) {
            group.setId(null);
            group.setAppId(rApp.getId());
            ConfGroup rGroup = confGroupRepository.save(group);
            for (Config config : group.getConfigs()) {
                config.setId(null);
                config.setAppId(rGroup.getAppId());
                config.setGroupId(rGroup.getId());
                configRepository.save(config);
            }
        }
        return true;
    }
}
