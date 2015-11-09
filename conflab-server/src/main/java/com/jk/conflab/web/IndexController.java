package com.jk.conflab.web;


import com.jk.conflab.model.User;
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
@RequestMapping("/")
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private User user;

    @RequestMapping("/u")
    @ResponseBody
    User test() {
        logger.info("-------------------root-----------------");
        return user;
    }

    @RequestMapping("hi")
    @ResponseBody
    String greet(@RequestParam String name) {
        return "hello" + name;
    }
}
