package com.jk.conflab.web;

import com.jk.conflab.model.ConfGroup;
import com.jk.conflab.service.ConfGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/11.
 */
@RestController
@RequestMapping("/group")
public class ConfGroupController {
    @Autowired
    ConfGroupService confGroupService;

    @RequestMapping("/app")
    List<ConfGroup> findByAppId(Long appId) {
        return confGroupService.findByAppId(appId);
    }
}
