package com.jk.conflab.repository;

import com.jk.conflab.model.App;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jacky.cheng on 2015/11/3.
 */
public interface AppRepository extends CrudRepository<App, Long> {
    @Query("select a from App a where a.name like %?1%")
    Iterable<App> findByNameLike(String key);
}
