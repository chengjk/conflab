package com.jk.conflab.service;

import com.jk.conflab.model.ConfGroup;

import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/10.
 */
public interface ConfGroupService {
    List<ConfGroup> findByAppId(Long appId);

    ConfGroup save(ConfGroup g) throws Exception;
    boolean del(Long id);

    ConfGroup exportOne(Long id);

    boolean importGroups(Long appId, List<ConfGroup> groups);

    ConfGroup update(ConfGroup o);

}
