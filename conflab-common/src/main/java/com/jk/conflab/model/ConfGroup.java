package com.jk.conflab.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/3.
 */
@Getter
@Setter
@NoArgsConstructor
public class ConfGroup implements Serializable {
    private String app;
    private String name;
    private String desc;
    private List<Config> configs=new ArrayList<Config>();
}
