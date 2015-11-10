package com.jk.conflab.service.impl;

import com.jk.conflab.Application;
import com.jk.conflab.service.ConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by jacky.cheng on 2015/11/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class ConfigServiceImplTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ConfigService configService;

    @Test
    public void testSave() throws Exception {
        configService.save(null);
    }

    @Test
    public void testFindByGroup() throws Exception {

    }
}