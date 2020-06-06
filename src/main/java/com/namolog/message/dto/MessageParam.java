package com.namolog.message.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class MessageParam {
    @NotBlank
    private String cinfo;
    @NotEmpty
    private String sendPhone;
    @NotBlank
    private String destPhone;
    private String replace1;
    private String replace2;
    private String replace3;
//    private String destName;
    private String requestTime;
    private String subject;
    private String attachedFile;
    @NotBlank
    private String msgBody;
}
