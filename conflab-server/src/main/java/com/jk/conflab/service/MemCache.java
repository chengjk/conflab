package com.jk.conflab.service;

import com.jk.conflab.model.App;
import com.jk.conflab.model.ConfGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by jacky. 2019/12/27 3:07 PM
 */
@Getter
@Setter
public class MemCache {
    static HashMap<String, App> localCache = new HashMap<>();

    public static Collection<App> getAll() {
        return localCache.values();
    }

    public static App getApp(String name) {
        return localCache.get(name);
    }

    public static List<App> findNameLike(String name) {
        return localCache.keySet().stream()
                .filter(f -> f.contains(name))
                .map(k -> localCache.get(k))
                .collect(Collectors.toList());
    }

    public static void add(App app) throws Exception {
        App cache = localCache.get(app.getName());
        if (cache != null) {
            throw new Exception("app name already exist! please try another.");
        }
        localCache.put(app.getName(), app);
    }

    public static App delApp(String name) {
        return localCache.remove(name);
    }

    public static void update(String srcName, App app) throws Exception {
        App cache = localCache.get(srcName);
        if (cache == null) {
            throw new Exception(" can not update invalid app! please try another.");
        }
        localCache.remove(srcName);
        localCache.put(app.getName(), app);
    }

    public static void add(ConfGroup o) {
        App app = getApp(o.getApp());
        app.getGroups().add(o);
    }

    public static void addAll(List<App> apps) throws Exception {
        for (App app : apps) {
            MemCache.add(app);
        }
    }
}
