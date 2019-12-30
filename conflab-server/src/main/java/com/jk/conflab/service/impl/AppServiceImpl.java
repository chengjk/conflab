package com.jk.conflab.service.impl;

import com.alibaba.fastjson.JSON;
import com.jk.conflab.model.App;
import com.jk.conflab.model.ConfGroup;
import com.jk.conflab.service.AppService;
import com.jk.conflab.service.MemCache;
import com.jk.conflab.service.ZkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/10.
 */
@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private ZkService zkService;


    @PostConstruct
    public void init() throws Exception {
        List<App> apps = zkService.readAll();
        MemCache.addAll(apps);
    }

    @Override
    public Iterable<App> findAll() {
        return MemCache.getAll();
    }

    @Override
    public Iterable<App> findByName(String name) {
        return MemCache.findNameLike(name);
    }

    @Override
    public App save(App app) throws Exception {
        MemCache.add(app);
        return app;
    }

    @Override
    public App copy(String srcName, String tarName) throws Exception {
        App src = MemCache.getApp(srcName);
        App tar = new App();
        BeanUtils.copyProperties(src, tar);
        tar.setName(tarName);
        MemCache.add(tar);
        return tar;
    }

    @Override
    @Transactional
    public boolean del(String name) {
        MemCache.delApp(name);
        zkService.delete(name);
        return true;
    }

    @Override
    public boolean push(String appName) {
        App app = MemCache.getApp(appName);
        return zkService.publish(appName, JSON.toJSONString(app));
    }

    @Override
    public boolean pushAll(String key) {
        Iterable<App> apps = MemCache.getAll();
        for (App app : apps) {
            if (!push(app.getName())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public App exportOne(String appName) {
        return MemCache.getApp(appName);
    }

    @Override
    public Iterable<App> exportByKey(String key) {
        return null;
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
        MemCache.add(app);
        return true;
    }

    @Override
    public App update(String srcName, App o) throws Exception {
        MemCache.update(srcName, o);
        return o;
    }

    @Override
    public void addGroup(String appName, List<ConfGroup> groups) throws Exception {
        App app = MemCache.getApp(appName);
        app.getGroups().addAll(groups);
        MemCache.update(appName, app);

    }

    @Override
    public void updateGroup(String srcName, ConfGroup group) {
        App app = MemCache.getApp(group.getApp());
        app.getGroups().stream()
                .filter(f -> f.getName().equals(srcName))
                .findFirst().ifPresent(tar -> {
            tar.setName(group.getName());
            tar.setDesc(group.getDesc());
        });

    }

    @Override
    public boolean delGroup(String appName, String groupName) {
        App app = MemCache.getApp(appName);
        List<ConfGroup> has = app.getGroups();
        has.removeIf(f -> f.getName().equals(groupName));
        return true;
    }
}
