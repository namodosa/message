package com.namolog.message.service;

import com.namolog.message.domain.NmRole;

import java.util.List;

public interface RoleService {
    NmRole getRole(int id);
    List<NmRole> getRoles();
    void createRole(NmRole nmRole);
    void deleteRole(int id);
}