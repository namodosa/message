package com.namolog.message.security.service;

import com.namolog.message.domain.NmAccount;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
@Setter
public class AccountContext extends User {

    private NmAccount nmAccount;

    public AccountContext(NmAccount nmAccount, List<GrantedAuthority> roles) {
        super(nmAccount.getUsername(), nmAccount.getPassword(), roles);
        this.nmAccount = nmAccount;
    }

}
