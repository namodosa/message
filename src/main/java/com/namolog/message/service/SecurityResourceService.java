package com.namolog.message.service;

import com.namolog.message.security.common.ResourceType;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;
import java.util.List;

public interface SecurityResourceService {
    LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getUrlResourceList();
    LinkedHashMap<String, List<ConfigAttribute>> getHttpMethodResourceList(ResourceType resourceType);
    List<String> getAccessIpList();
}
