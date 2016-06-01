package com.jk.conflab.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/3.
 */
@Getter
@Setter
@NoArgsConstructor
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
}
