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
public class NmAccount implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 20, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<NmAuthority> authorityList = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<SendPhone> sendPhoneList = new ArrayList<>();

    // 생성 메서드
    public NmAccount(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
