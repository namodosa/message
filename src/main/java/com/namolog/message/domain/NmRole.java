package com.namolog.message.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NmRole implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<NmAuthority> authorityList = new ArrayList<>();

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<NmRoleResource> roleResourceList = new ArrayList<>();

    public NmRole(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
