package com.jk.conflab.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.BeanProperty;
import com.jk.conflab.model.App;
import com.jk.conflab.model.ConfGroup;
import com.jk.conflab.model.Config;
import com.jk.conflab.repository.AppRepository;
import com.jk.conflab.repository.ConfGroupRepository;
import com.jk.conflab.repository.ConfigRepository;
import com.jk.conflab.service.AppService;
import com.jk.conflab.service.ZkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Iterable<App> findByName(String name) {
        return appRepository.findByName(name);
    }

    @Override
    public App save(App app) throws Exception {
        Iterable<App> byName = findByName(app.getName());
        if (byName.iterator().hasNext()) {
            throw new Exception("already exist! please try another.");
        }else {
            return appRepository.save(app);
        }
    }

    @Override
    public App copy(Long srcId, String tarName) throws Exception {
        App src = appRepository.findOne(srcId);
        App tar=new App();
        BeanUtils.copyProperties(src,tar);
        tar.setId(null);
        tar.setName(tarName);
        tar =save(tar);
        List<ConfGroup> groups = confGroupRepository.findByAppId(srcId);
        List<ConfGroup> tarGroups=new ArrayList<>();
        for (ConfGroup group : groups) {
            ConfGroup tarGroup=new ConfGroup();
            BeanUtils.copyProperties(group,tarGroup);
            tarGroup.setId(null);
            tarGroup.setAppId(tar.getId());
            tarGroups.add(tarGroup);

            List<Config> configs = configRepository.findByGroupId(group.getId());
            List<Config> tarConfigs=new ArrayList<>();
            for (Config config : configs) {
                Config tarConfig=new Config();
                BeanUtils.copyProperties(config,tarConfig);
                tarConfig.setId(null);
                tarConfig.setAppId(tar.getId());
                tarConfig.setGroupId(group.getId());
                tarConfigs.add(tarConfig);
            }
            configRepository.save(tarConfigs);
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
        App rApp = appRepository.save(app); // FIXME: 2016/2/21 唯一约束
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
