package com.jk.conflab.web;

import com.jk.conflab.model.App;
import com.jk.conflab.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/11.
 */
@RestController
@RequestMapping("/app")
public class AppController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AppService appService;

    @RequestMapping("/all")
    Iterable<App> findAll() {
        return appService.findAll();
    }

    @RequestMapping("/show/{app}")
    App findOne(@PathVariable String app) {
        return appService.exportOne(app);
    }

    @RequestMapping("/key/{key}")
    Iterable<App> findByKey(@PathVariable String key) {
        if (key == null | "".equals(key) || "null".equals(key) || "undefined".equals(key)) {
            return findAll();
        }
        return appService.findByName(key);
    }

    @RequestMapping("/add")
    App add(App o, HttpServletResponse resp) throws IOException {
        try {
            return appService.save(o);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resp.sendError(500, e.getMessage());
        }
        return null;
    }

    @RequestMapping("/update")
    App update(App o,String appName, HttpServletResponse resp) throws IOException {
        if (appName != null) {
            try {
                return appService.update(appName,o);
            } catch (Exception e) {
                resp.sendError(500, e.getMessage());
            }
        } else {
            logger.error("试图更新不正确的App。");
        }
        return null;
    }

    @RequestMapping("/del")
    boolean del(String appName) {
        return appService.del(appName);
    }

    @RequestMapping("/cp")
    App copy(String srcName, String tarName) throws Exception {
        return appService.copy(srcName, tarName);
    }

    @RequestMapping("/importAll")
    boolean importApps(List<App> apps) throws Exception {
        return appService.importApps(apps);
    }

    @RequestMapping("/import")
    boolean importApp(@RequestBody App app) throws Exception {
        if (app != null) {
            if (StringUtils.hasText(app.getName())) {
                return appService.importApp(app);
            }
        }
        throw new Exception("App对象解析失败。");
    }

    @RequestMapping("/push")
    boolean push(String appName) {
        return appService.push(appName);
    }

    @RequestMapping("/pushAll")
    boolean pushAll(String key) {
        return appService.pushAll(key);
    }


}
