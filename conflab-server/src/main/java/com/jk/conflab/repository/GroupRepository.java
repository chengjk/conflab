package com.jk.conflab.repository;

import com.jk.conflab.model.ConfGroup;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jacky.cheng on 2015/11/3.
 */
public interface GroupRepository extends CrudRepository<ConfGroup, Long> {
}
