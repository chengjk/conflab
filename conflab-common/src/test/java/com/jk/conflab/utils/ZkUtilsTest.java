package com.jk.conflab.utils;

import com.alibaba.fastjson.JSON;
import com.jk.conflab.SaasType;
import com.jk.conflab.model.App;
import com.jk.conflab.model.ConfGroup;
import com.jk.conflab.model.Config;
import org.I0Itec.zkclient.ZkClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * created by jacky. 2019/12/27 11:57 AM
 */
public class ZkUtilsTest {



    public static void main(String[] args) {
        ZkClient client = new ZkClient("172.30.30.17:2181", 3000);
        client.setZkSerializer(new ZkUtils.StringSerializer("UTF-8"));


        App app=new App();
        app.setDesc("desc");
        ConfGroup g=new ConfGroup();
        g.setName("ttt");
        g.setDesc("sss");
        Config c=new Config();
        c.setKey("kkkk");
        c.setValue("vvvvv");
        c.setDesc("dddd");
        g.setConfigs(Arrays.asList(c));
        app.setGroups(Arrays.asList(g));
        app.setName("test");
        app.setType(SaasType.Normal);

        String path ="/conflab/test";
        client.deleteRecursive(path);

        client.createPersistent(path);
        client.writeData(path, JSON.toJSONString(app));

       String s = client.readData(path);
        App o = JSON.parseObject(s, App.class);
        System.out.println(JSON.toJSONString(o));
    }

}
