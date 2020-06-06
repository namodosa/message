package com.namolog.message.service.impl;

import com.namolog.message.domain.NmRoleHierarchy;
import com.namolog.message.repository.RoleHierarchyRepository;
import com.namolog.message.service.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

    private final RoleHierarchyRepository roleHierarchyRepository;

    public String findAllHierarchy() {
        List<NmRoleHierarchy> rolesHierarchy = roleHierarchyRepository.findAll();

        Iterator<NmRoleHierarchy> itr = rolesHierarchy.iterator();
        StringBuilder concatedRoles = new StringBuilder();
        while (itr.hasNext()) {
            NmRoleHierarchy nmRoleHierarchy = itr.next();
            if (nmRoleHierarchy.getParent() != null) {
                concatedRoles.append(nmRoleHierarchy.getParent().getChild());
                concatedRoles.append(" > ");
                concatedRoles.append(nmRoleHierarchy.getChild());
                concatedRoles.append("\n");
            }
        }
        return concatedRoles.toString();
    }
}
