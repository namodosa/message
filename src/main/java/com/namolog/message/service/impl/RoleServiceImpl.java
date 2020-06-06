package com.namolog.message.service.impl;

import com.namolog.message.domain.NmRole;
import com.namolog.message.repository.RoleRepository;
import com.namolog.message.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public NmRole getRole(int id) {
        return roleRepository.findById(id).orElse(new NmRole());
    }

    public List<NmRole> getRoles() {
        return roleRepository.findAll();
    }

    public void createRole(NmRole nmRole) {
        roleRepository.save(nmRole);
    }

    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }
}