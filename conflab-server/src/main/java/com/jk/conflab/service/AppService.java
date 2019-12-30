package com.jk.conflab.service;

import com.jk.conflab.model.App;
import com.jk.conflab.model.ConfGroup;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/10.
 */
public interface AppService {
    Iterable<App> findAll();
    Iterable<App> findByName(String name);
    App copy(String srcName, String tarName) throws Exception;

    App save(App app) throws Exception;
    boolean del(String name);

    boolean push(String appName);

    boolean pushAll(String key);

    App exportOne(String appName);

    Iterable<App> exportByKey(String key);

    boolean importApps(List<App> apps) throws Exception;
    boolean importApp(App app) throws Exception;

    App update(String srcName, App o) throws Exception;

    void addGroup(String appName, List<ConfGroup> groups) throws Exception ;

    boolean delGroup(String appName, String groupName);

    void updateGroup(String appName, ConfGroup group);
}
