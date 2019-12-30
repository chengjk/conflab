package com.jk.conflab.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
@Getter
@Setter
@NoArgsConstructor
public class Config implements Serializable {
    private String app;
    private String group;
    private String key;
    private String value;
    private String desc;
}
