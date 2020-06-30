package com.namolog.message.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BizLogSearchConditon {
    private Integer status;
    @NotBlank
    private String destPhone;
    @NotBlank
    private String sendPhone;
    private String cinfo;
    @NotBlank
    private String msgBody;
    private String fromRequestDate;
    private String toRequestDate;
    private String fromSendDate;
    private String toSendDate;
    private String telInfo;

}
