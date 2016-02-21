package com.jk.conflab.web;

import com.jk.conflab.model.App;
import com.jk.conflab.model.ConfGroup;
import com.jk.conflab.repository.ConfGroupRepository;
import com.jk.conflab.service.ConfGroupService;
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
@RequestMapping("/group")
public class ConfGroupController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ConfGroupService confGroupService;

    @Autowired
    ConfGroupRepository confGroupRepository;

    @RequestMapping("/app/{appId}")
    List<ConfGroup> findByAppId(@PathVariable Long appId) {
        return confGroupService.findByAppId(appId);
    }

    @RequestMapping("/{id}")
    ConfGroup findId(@PathVariable Long id) {
        return confGroupRepository.findOne(id);
    }

    @RequestMapping("/add")
    ConfGroup add(ConfGroup o, HttpServletResponse resp) throws IOException {
        try {
            return confGroupService.save(o);
        } catch (Exception e) {
            resp.sendError(500,e.getMessage());
            return null;
        }
    }


    @RequestMapping("/export/{id}")
    ConfGroup export(@PathVariable Long id) {
        return confGroupService.exportOne(id);
    }


    @RequestMapping("/import")
    boolean importGroup(Long appId,List<ConfGroup> groups){
        return confGroupService.importGroups(appId,groups);
    }
    @RequestMapping("/update")
    ConfGroup update(ConfGroup o,HttpServletResponse resp) throws IOException {
        if (o.getId() != null) {
            try {
                return confGroupService.save(o);
            } catch (Exception e) {
                resp.sendError(500,e.getMessage());
                return null;
            }
        }else {
            logger.error("试图更新不正确的Group。");
            return null;
        }
    }

    @RequestMapping("/del")
    boolean del(Long id) {
        return confGroupService.del(id);
    }


}
