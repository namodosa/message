package com.namolog.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    @NotBlank
    private Integer id;

    @NotBlank(message = "아이디를 입력해 주세요")
    private String username;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    private String password;

    private String repassword;

    @NotBlank(message = "이름을 입력해 주세요")
    private String name;

    @NotBlank(message = "휴대폰 번호를 입력해 주세요")
    private String mobile;

    @NotBlank(message = "메일을 입력해 주세요")
    @Email(message = "올바른 메일 형식을 입력해 주세요")
    private String email;

    private String di;

    @Builder.Default
    private List<String> roles = new ArrayList<>();
}


