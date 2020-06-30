package com.namolog.message.domain;

import com.namolog.message.security.common.ResourceType;
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
public class NmResource implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String httpMethod;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private ResourceType resourceType;

    @Column(nullable = false)
    private Integer orderNum;

    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
    private List<NmRoleResource> roleResourceList = new ArrayList<>();

    public NmResource(String name, String httpMethod, ResourceType resourceType, int orderNum) {
        this.name = name;
        this.httpMethod = httpMethod;
        this.resourceType = resourceType;
        this.orderNum = orderNum;
    }
}
