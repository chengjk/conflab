package com.jk.conflab.web;


import com.jk.conflab.model.App;
import com.jk.conflab.model.ConfGroup;
import com.jk.conflab.model.Config;
import com.jk.conflab.repository.AppRepository;
import com.jk.conflab.repository.ConfGroupRepository;
import com.jk.conflab.repository.ConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jacky.cheng on 2015/10/26.
 */
@Controller
@RequestMapping("t")
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AppRepository appRepository;
    @Autowired
    ConfGroupRepository confGroupRepository;
    @Autowired
    ConfigRepository configRepository;
    @Autowired
    String test;

    @RequestMapping("hi")
    @ResponseBody
    String greet(@RequestParam String name) {
        return "hello " + name;
    }

    @RequestMapping("str")
    @ResponseBody
    String Str() {
        return test;
    }

    @RequestMapping("save")
    @ResponseBody
    String save() {
        try {
            App app = new App();
            app.setName("test");
            app.setDescp("test");
            app = appRepository.save(app);

            ConfGroup group = new ConfGroup();
            group.setAppId(app.getId());
            group.setName("test");
            group.setDescp("test");
            group = confGroupRepository.save(group);

            Config config = new Config();
            config.setAppId(app.getId());
            config.setGroupId(group.getId());
            config.setKey("testk");
            config.setValue("testv");
            config.setDescp("testd");
            configRepository.save(config);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "ok";
    }
}
