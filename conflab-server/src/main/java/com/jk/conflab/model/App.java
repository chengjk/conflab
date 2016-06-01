package com.jk.conflab.model;




import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jacky.cheng on 2015/11/3.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class App implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;
    private String descp;
    @Transient
    private List<ConfGroup> groups;

}
