package com.jk.conflab.model;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"groupId", "`key`"}))
public class Config implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private Long appId;
    private Long groupId;
    @Column(name = "`key`")
    private String key;
    @Column(name = "`value`")
    private String value;
    private String descp;

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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }
}
