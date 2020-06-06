package com.namolog.message.controller.user;

import com.namolog.message.domain.NmAccount;
import com.namolog.message.dto.AccountDto;
import com.namolog.message.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

//    private final AccountService accountService;
//    private final PasswordEncoder passwordEncoder;
//
//    @GetMapping("/users")
//    public String createUser() {
//        return "user/login/register";
//    }
//
//    @PostMapping("/users")
//    public String createUser(AccountDto accountDto) {
//        ModelMapper modelMapper = new ModelMapper();
//        NmAccount nmAccount = modelMapper.map(accountDto, NmAccount.class);
//        nmAccount.setPassword(passwordEncoder.encode(nmAccount.getPassword()));
//        accountService.createUser(nmAccount);
//        return "redirect:/";
//    }
}
