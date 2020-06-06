package com.namolog.message.repository;

import com.namolog.message.domain.NmResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<NmResource, Integer> {

    Optional<NmResource> findByNameAndHttpMethod(String name, String httpMethod);

    @Query("select r from NmResource r join fetch r.roleResourceList rr join fetch rr.role where r.id = :id")
    List<NmResource> findByIdWithJoin(@Param("id") Integer id);

    @Query("select r from NmResource r join fetch r.roleResourceList rr join fetch rr.role")
    List<NmResource> findAllWithJoin();

    @Query("select r from NmResource r join fetch r.roleResourceList rr join fetch rr.role where r.resourceType = 'URL' order by r.orderNum desc")
    List<NmResource> findAllByUrlWithJoin();

    @Query("select r from NmResource r join fetch r.roleResourceList rr join fetch rr.role where r.resourceType = 'METHOD' order by r.orderNum desc")
    List<NmResource> findAllByMethodWithJoin();

    @Query("select r from NmResource r join fetch r.roleResourceList rr join fetch rr.role where r.resourceType = 'POINTCUT' order by r.orderNum desc")
    List<NmResource> findAllByPointcutWithJoin();
}