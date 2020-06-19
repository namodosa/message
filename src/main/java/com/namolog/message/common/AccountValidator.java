package com.namolog.message.common;

import com.namolog.message.dto.AccountDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDto value = (AccountDto) target;

        // 비밀번호 검증
        if (!value.getPassword().equals(value.getRepassword())) {
            errors.rejectValue("repassword", "mismatchPassword", "입력한 비밀번호가 서로 다릅니다.");
        }

    }
}
