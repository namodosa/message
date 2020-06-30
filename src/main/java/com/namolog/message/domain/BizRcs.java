package com.namolog.message.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BizRcs {
    @Id
    @Column(length = 32, nullable = false)
    private String refkey;

    @Column(length = 40)
    private String chatbotId;
    @Column(length = 1)
    private String header;
    @Column(length = 64)
    private String footer;
    @Column(length = 40)
    private String messagebaseId;
    @Column(length = 1)
    private String copyAllowed;
    @Column(length = 2000)
    private String rcsBody;
    @Column(length = 2000)
    private String buttons;
}
