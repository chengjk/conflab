package com.jk.conflab.service;

import com.jk.conflab.model.App;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/10.
 */
public interface AppService {
    Iterable<App> findAll();
    Iterable<App> findByName(String name);
    App copy(Long srcId, String tarName) throws Exception;

    App save(App app) throws Exception;
    boolean del(Long id);

    boolean push(Long appId, String appName);

    boolean pushAll(String key);

    App exportOne(Long id);

    Iterable<App> exportByKey(String key);

    boolean importApps(List<App> apps);
    boolean importApp(App app);

    App update(App o);
}
