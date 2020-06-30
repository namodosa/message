package com.namolog.message.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "bizlog_dest_phone_idx", columnList = "destPhone"),
        @Index(name = "bizlog_send_phone_idx", columnList = "sendPhone")
})
public class BizLog {
    @Id
    @Column(length = 32)
    private String cmid;

    @Column(length = 32)
    private String umid;

    private Integer msgType;
    private Integer status;
    private LocalDateTime requestTime;
    private LocalDateTime sendTime;
    private LocalDateTime reportTime;

    @Column(length = 16)
    private String destPhone;
    @Column(length = 32)
    private String destName;
    @Column(length = 16)
    private String sendPhone;
    @Column(length = 32)
    private String sendName;
    @Column(length = 64)
    private String subject;
    @Column(length = 2000)
    private String msgBody;
    @Column(length = 5)
    private String nationCode;
    @Column(length = 40)
    private String senderKey;
    @Column(length = 30)
    private String templateCode;
    @Column(length = 8)
    private String responseMethod;
    private Integer timeout;
    @Column(length = 3)
    private String reType;
    @Column(length = 2000)
    private String reBody;
    @Column(length = 1)
    private String rePart;
    @Column(length = 80)
    private String wapUrl;
    private Integer coverFlag = 0;
    private Integer smsFlag = 0;
    private Integer replyFlag = 0;
    private Integer retryCnt;
    @Column(length = 1000)
    private String attachedFile;
    @Column(length = 64)
    private String vxmlFile;
    @Column(length = 4)
    private String callStatus;
    private Integer usePage = 0;
    private Integer useTime = 0;
    private Integer snResult = 0;
    @Column(length = 10)
    private String telInfo;
    @Column(length = 32)
    private String cinfo;
    @Column(length = 30)
    private String userKey;
    @Column(length = 1)
    private String adFlag;
    @Column(length = 32)
    private String rcsRefkey;

}
