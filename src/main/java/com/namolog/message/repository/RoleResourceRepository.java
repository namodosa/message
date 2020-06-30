package com.namolog.message.repository;

import com.namolog.message.domain.NmRoleResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleResourceRepository extends JpaRepository<NmRoleResource, Integer> {
    Optional<NmRoleResource> findByRoleIdAndResourceId(Integer roleId, Integer resourceId);
    List<NmRoleResource> findByResourceId(Integer resourceId);
}
