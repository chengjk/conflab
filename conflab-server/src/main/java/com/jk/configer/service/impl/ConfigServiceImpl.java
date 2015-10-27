package com.jk.configer.service.impl;

import com.jk.configer.model.Config;
import com.jk.configer.repository.ConfigRepository;
import com.jk.configer.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    ConfigRepository repository;
    public void save(Config conf) {
        repository.save(conf);
    }
}
