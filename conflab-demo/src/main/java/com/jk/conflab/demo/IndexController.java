package com.jk.conflab.demo;

import com.jk.conflab.client.ConfLab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/3/26.
 */
@RestController
@RequestMapping("/test")
public class IndexController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @RequestMapping("/conf")
    public
    @ResponseBody
    String getConfig(String key) {
        String result=null;
        if (key != null) {
            result = ConfLab.getString(key);
        }else {
            key = "null";
            result = "key is null";
        }
        logger.info("get config .{}:{}",key,result);
        return result;
    }
}
