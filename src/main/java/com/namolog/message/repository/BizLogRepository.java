package com.namolog.message.repository;

import com.namolog.message.domain.BizLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BizLogRepository extends JpaRepository<BizLog, String>, BizLogRepositoryCustom {

    Page<BizLog> findByDestPhone(String destPhone, Pageable pageable);

}
