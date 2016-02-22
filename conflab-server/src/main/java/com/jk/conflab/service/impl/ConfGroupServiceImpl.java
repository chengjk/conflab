package com.jk.conflab.service.impl;

import com.jk.conflab.model.ConfGroup;
import com.jk.conflab.model.Config;
import com.jk.conflab.repository.ConfGroupRepository;
import com.jk.conflab.repository.ConfigRepository;
import com.jk.conflab.service.ConfGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/10.
 */
@Service
public class ConfGroupServiceImpl implements ConfGroupService {
    @Autowired
    ConfGroupRepository confGroupRepository;
    @Autowired
    ConfigRepository configRepository;
    @Override
    public List<ConfGroup> findByAppId(Long appId) {
        return confGroupRepository.findByAppId(appId);
    }

    @Override
    public ConfGroup save(ConfGroup g) throws Exception {
        Iterable<ConfGroup> groups=confGroupRepository.findByAppIdAndName(g.getAppId(), g.getName());
        if (groups.iterator().hasNext()) {
            throw new Exception("already exist! please try another.");
        }else {
            return confGroupRepository.save(g);
        }
    }

    @Override
    @Transactional
    public boolean del(Long id) {
        confGroupRepository.delete(id);
        configRepository.deleteByGroupId(id);
        return true;
    }

    @Override
    public ConfGroup exportOne(Long id) {
        ConfGroup one = confGroupRepository.findOne(id);
        List<Config> configs = configRepository.findByGroupId(id);
        one.setConfigs(configs);
        return one;
    }

    @Override
    public boolean importGroups(Long appId, List<ConfGroup> groups) {
        for (ConfGroup group : groups) {
            group.setId(null);
            group.setAppId(appId);
            ConfGroup rGroup = confGroupRepository.save(group);// FIXME: 2016/2/21  唯一约束
            for (Config config : group.getConfigs()) {
                config.setId(null);
                config.setAppId(rGroup.getAppId());
                config.setGroupId(rGroup.getId());
                configRepository.save(config);
            }
        }
        return true;
    }
}
