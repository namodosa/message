package com.namolog.message.security.service;

import com.namolog.message.domain.NmAccount;
import com.namolog.message.domain.NmAuthority;
import com.namolog.message.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NmAccount nmAccount = accountRepository.findByUsernameWithJoin(username).orElse(null);
        if (nmAccount == null)
            throw new UsernameNotFoundException("No user found with username : " + username);

        List<GrantedAuthority> roles = new ArrayList<>();
        for (NmAuthority nmAuthority : nmAccount.getAuthorityList())
            roles.add(new SimpleGrantedAuthority(nmAuthority.getRole().getName()));

        return new AccountContext(nmAccount, roles);
    }

}
