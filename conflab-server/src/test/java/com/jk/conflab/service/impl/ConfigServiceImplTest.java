package com.jk.conflab.service.impl;

import com.jk.conflab.model.Config;
import com.jk.conflab.service.ConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jacky.cheng on 2015/11/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {ConfigServiceImpl.class})
public class ConfigServiceImplTest {
    @Autowired
    private ConfigService configService;

    @Test
    public void testName() throws Exception {
        System.out.println("Asdf");

    }

    @Test
    public void testSave() throws Exception {
        Config conf = new Config();
        conf.setGroupId(1L);
        configService.save(conf);
        System.out.println("asdfasdf");
    }

    @Test
    public void testFindByGroup() throws Exception {

    }
}