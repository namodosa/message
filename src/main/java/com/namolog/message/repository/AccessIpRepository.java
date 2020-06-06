package com.namolog.message.repository;


import com.namolog.message.domain.NmAccessIp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessIpRepository extends JpaRepository<NmAccessIp, Integer> {

    NmAccessIp findByAddress(String IpAddress);
}
