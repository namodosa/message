package com.namolog.message.service;

import com.namolog.message.domain.NmAccount;
import com.namolog.message.dto.AccountDto;

import java.util.List;

public interface AccountService {
    void createUser(NmAccount nmAccount);
    void modifyUser(AccountDto accountDto);
    List<NmAccount> getUsers();
    AccountDto getUser(int id);
    void deleteUser(int idx);
    void order();
}
