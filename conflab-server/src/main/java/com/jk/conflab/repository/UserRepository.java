package com.jk.conflab.repository;

import com.jk.conflab.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jacky.cheng on 2015/11/3.
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
