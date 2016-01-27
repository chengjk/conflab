package com.jk.conflab.web;

import com.jk.conflab.model.App;
import com.jk.conflab.repository.AppRepository;
import com.jk.conflab.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jacky.cheng on 2015/11/11.
 */
@RestController
@RequestMapping("/app")
public class AppController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AppService appService;
    @Autowired
    AppRepository appRepository;

    @RequestMapping("/all")
    Iterable<App> findAll() {
        return appService.findAll();
    }

    @RequestMapping("/{id}")
    App findOne(@PathVariable Long id) {
        return appRepository.findOne(id);
    }

    @RequestMapping("/key/{key}")
    Iterable<App> findByKey(@PathVariable String key) {
        if (key == null) {
            return findAll();
        }
        return appRepository.findByNameLike(key);
    }

    @RequestMapping("/add")
    App add(App o) {
        return appRepository.save(o);
    }

    @RequestMapping("/update")
    App update(App o) {
        if (o.getId() != null) {
            return appRepository.save(o);
        }else {
            logger.error("试图更新不正确的App。");
            return null;
        }
    }

    @RequestMapping("/del")
    boolean del(Long appId) {
        return appService.del(appId);
    }

    @RequestMapping("/cp")
    App copy(Long srcId, String tarName) {
        return appService.copy(srcId, tarName);
    }

    @RequestMapping("/push")
    boolean push(Long appId, String appName) {
        return appService.push(appId, appName);
    }

    @RequestMapping("/pushAll")
    boolean pushAll(String key) {
        return appService.pushAll(key);
    }


}
