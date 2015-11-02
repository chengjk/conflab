package com.jk.conflab.web;

import com.jk.conflab.model.Config;
import com.jk.conflab.repository.ConfigRepository;
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
    ConfigRepository configService;

    @RequestMapping("/all")
     Iterable<Config> findAll(){
        return configService.findAll();
    }

    @RequestMapping("/save")
    String save() {
        Config config=new Config();
        config.setApp("app");
        config.setGroup("g");
        config.setKey("k");
        config.setValue("v");
        config.setDesc("d");
        configService.save(config);
        return "ok";
    }
}
