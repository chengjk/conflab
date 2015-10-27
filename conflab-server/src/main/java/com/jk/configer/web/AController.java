package com.jk.configer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
@RestController
public class AController {
    @RequestMapping("/a")
    String a() {
        return "a";
    }
}
