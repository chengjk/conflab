package com.jk.conflab.service;

import com.jk.conflab.model.Config;

import java.util.List;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
public interface ConfigService {
    Config save(Config conf) throws Exception;

    List<Config> findByGroup(Long gId);

    Iterable<Config> findAll();

    Config update(Config o);

    void copy(Long srcId, Long tarId);
}
