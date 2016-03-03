package com.jk.conflab.repository;

import com.jk.conflab.model.Config;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
public interface ConfigRepository extends CrudRepository<Config,Long> {
    List<Config> findByGroupId(Long groupId);

    List<Config> findByAppId(Long appId);

    void deleteByGroupId(Long groupId);

    Iterable<Config> findByAppIdAndKey(Long appId, String key);

    Long deleteByAppId(Long id);
}
