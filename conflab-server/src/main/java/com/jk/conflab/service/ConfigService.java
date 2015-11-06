package com.jk.conflab.service;

import com.jk.conflab.model.Config;

import java.util.List;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
public interface ConfigService {
    void save(Config conf);

    List<Config> findByGroup(Long gId);
}
