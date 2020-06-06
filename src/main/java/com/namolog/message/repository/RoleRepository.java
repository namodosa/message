package com.namolog.message.repository;



import com.namolog.message.domain.NmRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<NmRole, Integer> {
    Optional<NmRole> findByName(String name);

    @Override
    void delete(NmRole nmRole);
}
