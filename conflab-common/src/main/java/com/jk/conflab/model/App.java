package com.jk.conflab.model;


import com.jk.conflab.SaasType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/3.
 */
@Getter
@Setter
@NoArgsConstructor
public class App implements Serializable {
    private String name;
    private SaasType type = SaasType.Normal;
    private String desc;
    private List<ConfGroup> groups = new ArrayList<ConfGroup>();

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        for (ConfGroup group : groups) {
            for (Config config : group.getConfigs()) {
                result.put(config.getKey(), config.getValue());
            }
        }
        return result;
    }
}
