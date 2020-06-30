package com.namolog.message.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NmAuthority implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private NmAccount account;  // FK - account_id

    @ManyToOne(fetch = FetchType.LAZY)
    private NmRole role;  // FK - role_id

    public NmAuthority(NmAccount account, NmRole role) {
        this.account = account;
        this.role = role;
        this.account.getAuthorityList().add(this);
        this.role.getAuthorityList().add(this);
    }
}
