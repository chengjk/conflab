package com.jk.conflab.client;

import org.I0Itec.zkclient.IZkDataListener;

/**
 * Created by Administrator on 2016/2/28.
 */
public interface IConfigListener extends IZkDataListener {
    String getAppId();
}
