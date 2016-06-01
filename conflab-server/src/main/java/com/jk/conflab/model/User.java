package com.jk.conflab.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by jacky.cheng on 2015/10/26.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String alias;
    private String pw;
    public User(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }
}
