package com.jk.conflab.service;

import com.jk.conflab.model.App;

/**
 * Created by jacky.cheng on 2015/11/10.
 */
public interface AppService {
    Iterable<App> findAll();

    App copy(Long srcId, String tarName);
}
