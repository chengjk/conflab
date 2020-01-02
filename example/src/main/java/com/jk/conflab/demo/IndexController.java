package com.jk.conflab.demo;

import com.jk.conflab.client.ConfLab;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2016/3/26.
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class IndexController {
    @RequestMapping("/conf")
    public String getConfig(String key) {
        String result = null;
        if (key != null) {
            result = ConfLab.getString(key);
        } else {
            key = "null";
            result = "key is null";
        }
        log.info("get config:{}={}", key, result);
        return result;
    }
}
