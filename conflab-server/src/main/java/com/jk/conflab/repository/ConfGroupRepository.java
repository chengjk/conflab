package com.jk.conflab.repository;

import com.jk.conflab.model.ConfGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/3.
 */
public interface ConfGroupRepository extends CrudRepository<ConfGroup, Long> {
    List<ConfGroup> findByAppId(Long appId);
    Long deleteByAppId(Long appId);
    Iterable<ConfGroup> findByAppIdAndName(Long appId, String name);
}
