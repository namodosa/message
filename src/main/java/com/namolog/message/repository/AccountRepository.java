package com.namolog.message.repository;

import com.namolog.message.domain.NmAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<NmAccount, Integer> {

    @Query("select a from NmAccount a join fetch a.authorityList al join fetch al.role")
    List<NmAccount> findAllWithJoin();

    @Query("select a from NmAccount a join fetch a.authorityList al join fetch al.role where a.username = :username")
    Optional<NmAccount> findByUsernameWithJoin(@Param("username") String username);

    @Query("select a from NmAccount a join fetch a.authorityList al join fetch al.role where a.id = :id")
    Optional<NmAccount> findByIdWithJoin(@Param("id") Integer id);

    Optional<NmAccount> findByUsername(String username);
}
