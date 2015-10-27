package com.jk.configer.repository;

import com.jk.configer.model.Config;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
public interface ConfigRepository extends CrudRepository<Config,Long> {
}
