package com.namolog.message.repository;

import com.namolog.message.domain.SendPhone;
import com.namolog.message.dto.SendPhoneDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SendPhoneRepository extends JpaRepository<SendPhone, Integer> {

    @Query("select new com.namolog.message.dto.SendPhoneDto(p.id, p.phone, p.status, p.regMethod, p.postDate, p.regDate) from SendPhone p where p.account.id = :accountId")
    List<SendPhoneDto> findAllByAccountId(@Param("accountId") Integer accountId);

    @Query("select new com.namolog.message.dto.SendPhoneDto(p.id, p.phone, p.status, p.regMethod, p.postDate, p.regDate) from SendPhone p where p.status = 1 and p.account.id = :accountId")
    List<SendPhoneDto> findAllByAccountIdAndAccept(Integer id);
}
