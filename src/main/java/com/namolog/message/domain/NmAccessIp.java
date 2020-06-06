package com.namolog.message.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NmAccessIp implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 20, nullable = false)
    private String address;

}
