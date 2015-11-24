package com.jk.conflab.client;

import org.I0Itec.zkclient.IZkDataListener;

public class ConfigListenerAdapter implements IZkDataListener {
    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        //TODO impl.assign to jacky.cheng
        throw new RuntimeException("没有实现!");

    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        //TODO impl.assign to jacky.cheng
        throw new RuntimeException("没有实现!");

    }
}