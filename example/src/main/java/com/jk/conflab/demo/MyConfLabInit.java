package com.jk.conflab.demo;

import com.jk.conflab.client.DefaultConfListener;
import com.jk.conflab.client.WithCommonConfLabStarter;
import org.I0Itec.zkclient.IZkDataListener;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MyConfLabInit extends WithCommonConfLabStarter {

    @Override
    protected String getAppId() {
        return "ConflabDemo";
    }

    @Override
    protected IZkDataListener getListener() {
        return new DefaultConfListener();
    }
}
