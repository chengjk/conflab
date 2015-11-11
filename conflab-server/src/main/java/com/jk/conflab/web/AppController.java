package com.jk.conflab.web;

import com.jk.conflab.model.App;
import com.jk.conflab.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/all")
    Iterable<App> findAll() {
        return appService.findAll();
    }
}
