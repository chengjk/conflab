package com.jk.configer.repository;

import com.jk.configer.po.Config;
import org.springframework.data.repository.Repository;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
public interface ConfigRepository extends Repository<Config,Long> {
    Config findOne(Long id);
}
