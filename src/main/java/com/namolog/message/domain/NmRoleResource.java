package com.namolog.message.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NmRoleResource implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private NmRole role;  // FK - role_id

    @ManyToOne
    private NmResource resource;  // FK - resource_id

    public NmRoleResource(NmRole role, NmResource resource) {
        this.role = role;
        this.resource = resource;
    }
}
