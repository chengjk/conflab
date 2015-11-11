package com.jk.conflab.service.impl;

import com.jk.conflab.model.ConfGroup;
import com.jk.conflab.repository.ConfGroupRepository;
import com.jk.conflab.service.ConfGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/10.
 */
@Service
public class ConfGroupServiceImpl implements ConfGroupService {
    @Autowired
    ConfGroupRepository confGroupRepository;

    @Override
    public List<ConfGroup> findByAppId(Long appId) {
        return confGroupRepository.findByAppId(appId);
    }
}
