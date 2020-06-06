package com.namolog.message.service.impl;

import com.namolog.message.domain.NmAccount;
import com.namolog.message.domain.NmAuthority;
import com.namolog.message.domain.NmRole;
import com.namolog.message.dto.AccountDto;
import com.namolog.message.repository.AccountRepository;
import com.namolog.message.repository.AuthorityRepository;
import com.namolog.message.repository.RoleRepository;
import com.namolog.message.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("accountService")
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(NmAccount nmAccount){
        NmRole nmRole = roleRepository.findByName("ROLE_USER").orElse(new NmRole("ROLE_USER", "사용자 권한"));
        NmAuthority nmAuthority = new NmAuthority(nmAccount, nmRole);
        nmAccount.getAuthorityList().add(nmAuthority);
        accountRepository.save(nmAccount);
    }

    public void modifyUser(AccountDto accountDto){
        NmAccount nmAccount = accountRepository.findById(accountDto.getId()).orElse(null);
        nmAccount.setUsername(accountDto.getUsername());
        if (StringUtils.hasText(accountDto.getPassword()))
            nmAccount.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        nmAccount.setEmail(accountDto.getEmail());
        accountRepository.save(nmAccount);

        List<NmAuthority> nmAuthorityList = authorityRepository.findByAccountId(nmAccount.getId());
        if (nmAuthorityList.size() > 0) {
            authorityRepository.deleteAll(nmAuthorityList);
            nmAuthorityList.clear();
        }
        if(accountDto.getRoles() != null) {
            List<NmRole> nmRoles = new ArrayList<>();
            accountDto.getRoles().forEach(r -> {
                NmRole nmRole = roleRepository.findByName(r).orElseThrow(() -> new NullPointerException("Cannot find role : " + r));
                NmAuthority nmAuthority = new NmAuthority(nmAccount, nmRole);
                authorityRepository.save(nmAuthority);
            });
        }
    }

    public AccountDto getUser(int id) {
        NmAccount nmAccount = accountRepository.findByIdWithJoin(id).orElse(new NmAccount());
        ModelMapper modelMapper = new ModelMapper();
        AccountDto accountDto = modelMapper.map(nmAccount, AccountDto.class);

        List<String> authorityList = nmAccount.getAuthorityList()
                .stream()
                .map(a -> a.getRole().getName())
                .collect(Collectors.toList());

        accountDto.setRoles(authorityList);
        return accountDto;
    }

    public List<NmAccount> getUsers() {
        List<NmAccount> nmAccountList = accountRepository.findAll();
        for (NmAccount nmAccount : nmAccountList) {
            List<NmAuthority> nmAuthorityList = authorityRepository.findByAccountId(nmAccount.getId());
            nmAccount.setAuthorityList(nmAuthorityList);
        }
        return nmAccountList;
    }

    public void deleteUser(int id) {
        accountRepository.deleteById(id);
    }

    @Secured("ROLE_MANAGER")
    public void order() {
        System.out.println("order");
    }
}