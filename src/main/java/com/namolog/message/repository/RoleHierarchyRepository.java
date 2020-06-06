package com.namolog.message.repository;


import com.namolog.message.domain.NmRoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleHierarchyRepository extends JpaRepository<NmRoleHierarchy, Integer> {

    NmRoleHierarchy findByChild(String roleName);
}
