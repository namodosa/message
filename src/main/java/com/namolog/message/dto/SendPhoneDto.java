package com.namolog.message.dto;

import com.namolog.message.common.RegMethod;
import com.namolog.message.domain.NmAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor
public class SendPhoneDto {

    private Integer id;
    @NotBlank(message = "전화번호를 입력해 주세요")
    private String phone;
    private Integer status;
    private RegMethod regMethod;
    private String postDate;
    private String regDate;

    public SendPhoneDto(Integer id, String phone, Integer status, RegMethod regMethod, LocalDateTime postDate, LocalDateTime regDate) {
        this.id = id;
        this.phone = phone;
        this.status = status;
        this.regMethod = regMethod;
        this.postDate = postDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (regDate != null)
            this.regDate = regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
