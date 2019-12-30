package com.jk.conflab.service;

import com.jk.conflab.model.App;

import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/16.
 */
public interface ZkService {
    boolean publish(String app, String configData);
    boolean delete(String app);

    List<App> readAll();
}
