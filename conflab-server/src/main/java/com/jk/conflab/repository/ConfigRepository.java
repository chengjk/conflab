package com.jk.conflab.repository;

import com.jk.conflab.model.Config;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
public interface ConfigRepository extends CrudRepository<Config,Long> {
    List<Config> findByGroupId(Long groupId);

    Config findById(Long id);

    Config findByAppId(Long appId);
}
