package com.jk.conflab.service;

import com.jk.conflab.model.ConfGroup;

import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/10.
 */
public interface ConfGroupService {
    List<ConfGroup> findByAppId(Long appId);

    boolean del(Long id);
}
