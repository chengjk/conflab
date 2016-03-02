package com.jk.conflab.service.impl;

import com.jk.conflab.model.Config;
import com.jk.conflab.repository.ConfigRepository;
import com.jk.conflab.service.ConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigRepository repository;

    public Config save(Config conf) throws Exception {
        Iterable<Config> configs = repository.findByAppIdAndKey(conf.getAppId(), conf.getKey());
        if (configs.iterator().hasNext()) {
            throw new Exception("already exist! please try another.");
        }else {
            return repository.save(conf);
        }
    }

    @Override
    public List<Config> findByGroup(Long groupId) {
        return repository.findByGroupId(groupId);
    }

    @Override
    public Iterable<Config> findAll() {
        return repository.findAll();
    }

    @Override
    public Config update(Config o) {
        return repository.save(o);
    }
    @Override
    public void copy(Long srcId, Long tarId) {
        List<Config> configs = repository.findByGroupId(srcId);
        List<Config> tarConfigs=new ArrayList<>();
        for (Config config : configs) {
            Config tarConfig=new Config();
            BeanUtils.copyProperties(config,tarConfig);
            tarConfig.setId(null);
            tarConfig.setAppId(config.getAppId());
            tarConfig.setGroupId(tarId);
            tarConfigs.add(tarConfig);
        }
        repository.save(tarConfigs);
    }
}
