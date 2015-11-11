package com.jk.conflab.service.impl;

import com.jk.conflab.model.App;
import com.jk.conflab.repository.AppRepository;
import com.jk.conflab.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jacky.cheng on 2015/11/10.
 */
@Service
public class AppServiceImpl implements AppService {
    @Autowired
    AppRepository appRepository;

    @Override
    public Iterable<App> findAll() {
        return appRepository.findAll();
    }
}
