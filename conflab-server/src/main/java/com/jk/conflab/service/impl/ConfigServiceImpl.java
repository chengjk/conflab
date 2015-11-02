package com.jk.conflab.service.impl;

import com.jk.conflab.model.Config;
import com.jk.conflab.repository.ConfigRepository;
import com.jk.conflab.service.ConfigService;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
public class ConfigServiceImpl implements ConfigService {
    private ConfigRepository repository;
    public void save(Config conf) {
        repository.save(conf);
    }
}
