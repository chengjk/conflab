package com.jk.conflab.service.impl;

import com.jk.conflab.Application;
import com.jk.conflab.BaseTest;
import com.jk.conflab.service.ConfigService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;

/**
 * Created by jacky.cheng on 2015/11/5.
 */
@SpringApplicationConfiguration(classes = {Application.class})
public class ConfigServiceImplTest extends BaseTest {
    @Autowired
    ConfigService configService;

    @Test
    public void testSave() throws Exception {
        configService.save(null);
    }

    @Test
    public void testFindByGroup() throws Exception {

    }
}