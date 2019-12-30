package com.jk.conflab.web;

import com.jk.conflab.model.App;
import com.jk.conflab.model.ConfGroup;
import com.jk.conflab.model.Config;
import com.jk.conflab.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
@Slf4j
@RestController
@RequestMapping("/conf")
public class ConfigController {
    @Autowired
    private AppService appService;

    @RequestMapping("{app}/{group}/{key}")
    public Config findById(@PathVariable String app, @PathVariable String group, @PathVariable String key) {
        App one = appService.exportOne(app);
        ConfGroup confGroup = one.getGroups().stream()
                .filter(f -> f.getName().equals(group))
                .findFirst()
                .orElse(null);
        Config config = confGroup.getConfigs().stream()
                .filter(f -> f.getKey().equals(key))
                .findFirst()
                .orElse(null);
        return config;
    }

    @RequestMapping("/group/all")
    public List<Config> findByGroupId(String app,String group) {

        App one = appService.exportOne(app);
        ConfGroup confGroup = one.getGroups().stream()
                .filter(f -> f.getName().equals(group))
                .findFirst().orElse(null);


        if (confGroup != null) {
            return confGroup.getConfigs();
        }
        return null;


    }

    @RequestMapping("/app/{app}")
    public List<Config> findByAppId(@PathVariable String app) {
        App one = appService.exportOne(app);
        List<Config> all = new ArrayList<>();
        for (ConfGroup group : one.getGroups()) {
            all.addAll(group.getConfigs());
        }
        return all;
    }

    @RequestMapping("/add")
    public Config add(Config o, HttpServletResponse resp) {
        App one = appService.exportOne(o.getApp());
        ConfGroup confGroup = one.getGroups().stream()
                .filter(f -> f.getName().equals(o.getGroup()))
                .findFirst()
                .orElse(null);
        if (confGroup == null) {
            confGroup = new ConfGroup();
            confGroup.setApp(o.getApp());
//        } else {
//            one.getGroups().remove(confGroup);
        }
        confGroup.getConfigs().add(o);

//        one.getGroups().add(confGroup);
        return null;
    }

    @RequestMapping("/update")
    public Config update(String srcKey,Config o, HttpServletResponse resp) throws IOException {
        App one = appService.exportOne(o.getApp());
        ConfGroup confGroup = one.getGroups().stream()
                .filter(f -> f.getName().equals(o.getGroup()))
                .findFirst()
                .orElse(null);
        Config config = confGroup.getConfigs().stream()
                .filter(f -> f.getKey().equals(srcKey))
                .findFirst()
                .orElse(null);
        config.setKey(o.getKey());
        config.setValue(o.getValue());
        config.setDesc(o.getDesc());
        return config;
    }


    @RequestMapping("/del")
    public boolean del(String app, String group, String key) {
        App one = appService.exportOne(app);
        for (ConfGroup oneGroup : one.getGroups()) {
            oneGroup.getConfigs().removeIf(f -> f.getKey().equals(key));

        }
        return true;
    }

}
