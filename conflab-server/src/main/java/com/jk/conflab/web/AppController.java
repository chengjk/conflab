package com.jk.conflab.web;

import com.jk.conflab.model.App;
import com.jk.conflab.repository.AppRepository;
import com.jk.conflab.service.AppService;
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


    @RequestMapping("/del")
    boolean del(Long id){
        return appService.del(id);
    }

    @RequestMapping("/cp")
    App copy(Long srcId, String tarName) {
        return appService.copy(srcId,tarName);
    }
    @RequestMapping("/push")
    boolean push(Long id) {
        return appService.push(id);
    }


}
