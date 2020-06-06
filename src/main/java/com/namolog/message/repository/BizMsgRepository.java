package com.namolog.message.repository;

import com.namolog.message.domain.BizMsg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BizMsgRepository extends JpaRepository<BizMsg, String> {


}
