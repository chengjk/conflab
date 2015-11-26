package com.jk.conflab.client;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.Assert.assertEquals;

/**
 * Created by jacky.cheng on 2015/11/26.
 */
public class ConfLabTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before
    public void myConfLabInit() {
        MyConfLabInit.isDev = false;
        new MyConfLabInit();
    }

    @Test
    public void getConfigData() {
        System.setProperty("conflab", "conflab_value"); //设置
        String value = ConfLab.getString("conflab"); // 读取
        assertEquals("get data from system properties failed!", "conflab_value", value);
        String test = ConfLab.getString("test");
        assertEquals("get data from zookeeper failed!", "test_value", test);
    }
}