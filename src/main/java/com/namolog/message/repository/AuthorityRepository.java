package com.namolog.message.repository;

import com.namolog.message.domain.NmAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<NmAuthority, Integer> {
    Optional<NmAuthority> findByAccountIdAndRoleId(Integer accountId, Integer roleId);

    //@Query("select a from Authority a where a.account = :accountId")
    List<NmAuthority> findByAccountId(Integer accountId);
}
