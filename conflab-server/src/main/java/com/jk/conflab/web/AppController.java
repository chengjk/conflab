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
        if (key == null|"".equals(key)||"null".equals(key)||"undefined".equals(key)) {
            return findAll();
        }
        return appRepository.findByNameLike(key);
    }

    @RequestMapping("/add")
    App add(App o, HttpServletResponse resp) throws IOException {
        try {
            return appService.save(o);
        } catch (Exception e) {
            resp.sendError(500,e.getMessage());
            return null;
        }
    }

    @RequestMapping("/update")
    App update(App o,HttpServletResponse resp) throws IOException {
        if (o.getId() != null) {
            try {
                return appService.save(o);
            } catch (Exception e) {
                resp.sendError(500,e.getMessage());
                return null;
            }
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
    App copy(Long srcId, String tarName) throws Exception {
        return appService.copy(srcId, tarName);
    }

    @RequestMapping("/export/{id}")
    App export(@PathVariable Long id) {
        return appService.exportOne(id);
    }

    @RequestMapping("/export/key/{key}")
    Iterable<App> export(@PathVariable String key) {
        return appService.exportByKey(key);
    }

    @RequestMapping("/import")
    boolean importApp(List<App> apps) {
        return appService.importApps(apps);
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
