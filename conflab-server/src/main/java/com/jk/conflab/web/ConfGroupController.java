package com.jk.conflab.web;

import com.jk.conflab.model.ConfGroup;
import com.jk.conflab.service.AppService;
import com.jk.conflab.service.MemCache;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/group")
public class ConfGroupController {

    @Autowired
    private AppService appService;


    @RequestMapping("/{app}/{group}")
    ConfGroup findId(@PathVariable String app, @PathVariable String group) {
        List<ConfGroup> groups = appService.exportOne(app).getGroups();
        return groups.stream().filter(f -> f.getName().equals(group)).findFirst().orElse(null);
    }

    @RequestMapping("/{app}/all")
    Iterable<ConfGroup> findAll(@PathVariable String app) {
        return appService.exportOne(app).getGroups();
    }

    @RequestMapping("/add")
    ConfGroup add(ConfGroup o, HttpServletResponse resp) throws IOException {
        MemCache.add(o);
        return o;
    }

    @RequestMapping("/import")
    boolean importGroup(String appName, List<ConfGroup> groups) throws Exception {
        appService.addGroup(appName, groups);
        return true;
    }

    /**
     *
     * @param srcName
     * @param o  appName 不会变
     * @param resp
     * @return
     * @throws IOException
     */
    @RequestMapping("/update")
    ConfGroup update(String srcName,ConfGroup o, HttpServletResponse resp) throws IOException {
        appService.updateGroup(srcName, o);
        return o;
    }

    @RequestMapping("/del")
    boolean del(String app,String group) {
        return appService.delGroup(app,group);
    }


}
