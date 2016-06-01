package com.jk.conflab.model;

import com.alibaba.fastjson.JSON;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/3.
 */
@Entity
public class App implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;

    private String descp;
    @Transient
    private List<ConfGroup> groups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<ConfGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<ConfGroup> groups) {
        this.groups = groups;
    }
}
