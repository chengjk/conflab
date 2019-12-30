package com.jk.conflab.demo;

import com.jk.conflab.client.DefaultConfListener;
import com.jk.conflab.client.WithCommonConfLabStarter;
import org.I0Itec.zkclient.IZkDataListener;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MyConfLabInit extends WithCommonConfLabStarter {

    @Override
    protected String getAppId() {
        return "example";
    }

    @Override
    protected String getZookeeper() {
        return "172.30.30.17:2181";
    }

    @Override
    protected IZkDataListener getListener() {
        return new DefaultConfListener();
    }
}
