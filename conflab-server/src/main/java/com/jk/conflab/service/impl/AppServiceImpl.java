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
import com.jk.conflab.service.ConfGroupService;
import com.jk.conflab.service.ZkService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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
    ConfGroupService confGroupService;
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
        confGroupService.copyByAppId(srcId,tar.getId());
        return tar;
    }

    @Override
    @Transactional
    public boolean del(Long id) {
        boolean flag;
        App one = appRepository.findOne(id);
        flag=zkService.delete(one.getName());
        appRepository.delete(id);
        confGroupRepository.deleteByAppId(id);
        configRepository.deleteByAppId(id);
        return flag;
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
    public boolean importApps(List<App> apps) throws Exception {
        for (App app : apps) {
            importApp(app);
        }
        return true;
    }

    @Override
    public boolean importApp(App app) throws Exception {
        app.setId(null);
        App rApp;
        try {
            rApp = appRepository.save(app);
        } catch (Exception e) {
            throw  new Exception("Duplicate entry for key name.");
        }
        if (rApp != null) {
            for (ConfGroup group : app.getGroups()) {
                group.setId(null);
                group.setAppId(rApp.getId());
                ConfGroup rGroup = confGroupRepository.save(group);
                group.getConfigs().forEach(g->{
                    g.setId(null);
                    g.setAppId(rGroup.getAppId());
                    g.setGroupId(rGroup.getId());
                    configRepository.save(g);
                });
            }
        }else {
            throw  new Exception("app save failed , break.");
        }
        return true;
    }

    @Override
    public App update(App o) {
        return appRepository.save(o);
    }
}
