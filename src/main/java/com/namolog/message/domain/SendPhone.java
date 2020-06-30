package com.namolog.message.domain;

import com.namolog.message.common.RegMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class SendPhone {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(nullable = false)
    private Integer status;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private RegMethod regMethod;

    @Column(nullable = false)
    private LocalDateTime postDate;

    private LocalDateTime regDate;

    @ManyToOne
    private NmAccount account;

}
