package com.jk.conflab.service;

/**
 * Created by jacky.cheng on 2015/11/16.
 */
public interface ZkService {
    boolean publish(String app, String configData);
    boolean delete(String app);
}
