package com.namolog.message.dto;

import com.namolog.message.common.MessageCode;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BizLogDto {
    private String id;
    private Integer status;
    private String msgType;
    private String resultMsg;
    private String sendTime;
    private String sendPhone;
    private String destPhone;
    private String subject;
    private String msgBody;
    private String telInfo;
    private String cinfo;
    private String attachedFile;

    @QueryProjection
    public BizLogDto(String cmid, int msgType, String callStatus, LocalDateTime sendTime,
                     String sendPhone, String destPhone, String msgBody, String telInfo, String cinfo) {
        MessageCode messageCode = new MessageCode();
        int resultCode = Integer.parseInt(callStatus);
        int status = 0; // 실패
        if (resultCode == 4100 || resultCode == 6600 || resultCode == 7000 || resultCode == 8000)
            status = 1; // 전송 완료
        this.id = cmid;
        this.status = status;
        this.msgType = messageCode.typeMap.get(msgType);
        this.resultMsg = messageCode.resultMap.get(resultCode);
        this.sendTime = sendTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.sendPhone = sendPhone;
        this.destPhone = destPhone;
        this.msgBody = this.msgType.equals("sms") ? msgBody : msgBody.substring(0, 90);
        this.telInfo = telInfo;
        this.cinfo = cinfo;
    }

    @QueryProjection
    public BizLogDto(String cmid, int msgType, String callStatus, LocalDateTime sendTime, String sendPhone,
                     String destPhone, String subject, String msgBody, String telInfo, String cinfo, String attachedFile) {
        MessageCode messageCode = new MessageCode();
        int resultCode = Integer.parseInt(callStatus);
        int status = 0; // 실패
        if (resultCode == 4100 || resultCode == 6600 || resultCode == 7000 || resultCode == 8000)
            status = 1; // 전송 완료
        this.id = cmid;
        this.status = status;
        this.msgType = messageCode.typeMap.get(msgType);
        this.resultMsg = messageCode.resultMap.get(resultCode);
        this.sendTime = sendTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.sendPhone = sendPhone;
        this.destPhone = destPhone;
        this.subject = subject;
        this.msgBody = msgBody;
        this.telInfo = telInfo;
        this.cinfo = cinfo;
        this.attachedFile = attachedFile;
    }
}
