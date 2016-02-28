package com.jk.conflab.demo;

import com.jk.conflab.client.DefaultConfLabInit;
import com.jk.conflab.client.DefaultConfListener;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MyConfLabInit extends DefaultConfLabInit {

    @Override
    protected String getAppId() {
        return "app_demo";
    }

    @Override
    protected DefaultConfListener getListener() {
        return new DefaultConfListener(getAppId());
    }
}
