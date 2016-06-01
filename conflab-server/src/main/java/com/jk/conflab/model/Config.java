package com.jk.conflab.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jacky.cheng on 2015/10/27.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"appId", "`key`"}))
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
}
