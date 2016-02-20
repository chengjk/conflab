package com.jk.conflab.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/3.
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"appId", "name"}))
public class ConfGroup implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Long appId;
    private String name;
    private String descp;
    @Transient
    private List<Config> configs;
    public ConfGroup() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    public List<Config> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }
}
