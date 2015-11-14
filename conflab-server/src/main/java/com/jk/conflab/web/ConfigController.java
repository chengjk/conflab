package com.jk.conflab.web;

import com.jk.conflab.model.Config;
import com.jk.conflab.repository.ConfigRepository;
import com.jk.conflab.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
@RestController
@RequestMapping("/conf")
public class ConfigController {
    @Autowired
    ConfigService configService;
    @Autowired
    ConfigRepository configRepository;

    ///---------------------------
    @RequestMapping("/tsave")
    String save() {
        Config config = new Config();
        config.setAppId(1L);
        config.setGroupId(1L);
        config.setKey("k");
        config.setValue("v");
        config.setDescp("d");
        configService.save(config);
        return "ok";
    }
    //---------------------------------


    @RequestMapping("id/{id}")
    Config findById(@PathVariable Long id) {
        return configRepository.findOne(id);
    }

    @RequestMapping("/group/{groupId}")
    List<Config> findByGroupId(@PathVariable Long groupId) {
        return configService.findByGroup(groupId);
    }

    @RequestMapping("/app/{appId}")
    List<Config>  findByAppId(@PathVariable Long appId) {
        return configRepository.findByAppId(appId);
    }

    @RequestMapping("/all")
    Iterable<Config> findAll() {
        return configService.findAll();
    }

    @RequestMapping("/del")
    boolean del(Long id) {
        configRepository.delete(id);
        return true;
    }

}
