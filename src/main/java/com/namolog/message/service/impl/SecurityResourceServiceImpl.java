package com.namolog.message.service.impl;

import com.namolog.message.domain.NmResource;
import com.namolog.message.repository.AccessIpRepository;
import com.namolog.message.repository.ResourceRepository;
import com.namolog.message.security.common.ResourceType;
import com.namolog.message.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.namolog.message.security.common.CommonUtil.existClassMethod;

@Service("securityResourceService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecurityResourceServiceImpl implements SecurityResourceService {

    private final ResourceRepository resourceRepository;
    private final AccessIpRepository accessIpRepository;

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getUrlResourceList() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<NmResource> resourcesList = resourceRepository.findAllByUrlWithJoin();
        resourcesList.forEach(r -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            r.getRoleResourceList().forEach(ro -> {
                configAttributeList.add(new SecurityConfig(ro.getRole().getName()));
            });
            result.put(new AntPathRequestMatcher(r.getName()), configAttributeList);
        });
        return result;
    }

    public LinkedHashMap<String, List<ConfigAttribute>> getHttpMethodResourceList(ResourceType resourceType) {
        LinkedHashMap<String, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<NmResource> resourcesList;
        if (resourceType == ResourceType.METHOD)
            resourcesList = resourceRepository.findAllByMethodWithJoin();
        else
            resourcesList = resourceRepository.findAllByPointcutWithJoin();
        for (NmResource r : resourcesList) {
            // class 가 있는지 체크
            if (resourceType == ResourceType.METHOD && !existClassMethod(r.getName()))
                continue;
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            r.getRoleResourceList().forEach(ro -> {
                configAttributeList.add(new SecurityConfig(ro.getRole().getName()));
            });
            result.put(r.getName(), configAttributeList);
        }
        return result;
    }

    public List<String> getAccessIpList() {
        List<String> accessIpList = accessIpRepository.findAll().stream().map(accessIp -> accessIp.getAddress()).collect(Collectors.toList());
        return accessIpList;
    }
}
