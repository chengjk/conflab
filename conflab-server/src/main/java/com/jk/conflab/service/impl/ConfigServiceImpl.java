package com.jk.conflab.service.impl;

import com.jk.conflab.model.Config;
import com.jk.conflab.repository.ConfigRepository;
import com.jk.conflab.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigRepository repository;

    public void save(Config conf) {
        repository.save(conf);
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
    public Config findById(Long id) {
        return repository.findOne(id);
    }
}
