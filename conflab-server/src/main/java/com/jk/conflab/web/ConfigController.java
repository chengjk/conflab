package com.jk.conflab.web;

import com.jk.conflab.model.Config;
import com.jk.conflab.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
@RestController
@RequestMapping("/conf")
public class ConfigController {
    @Autowired
    ConfigService configService;

    @RequestMapping("/all")
     Iterable<Config> findAll(){
        System.out.println("aasfdsdafasdfasf");
        configService.save(null);
        return null;
    }

    @RequestMapping("/save")
    String save() {
        Config config=new Config();
        config.setAppId(1L);
        config.setGroupId(1L);
        config.setKey("k");
        config.setValue("v");
        config.setDesc("d");
        configService.save(config);
        return "ok";
    }
}
