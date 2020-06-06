package com.namolog.message.security.factory;

import com.namolog.message.security.common.ResourceType;
import com.namolog.message.service.impl.SecurityResourceServiceImpl;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;

import java.util.LinkedHashMap;
import java.util.List;

public class MethodResourcesMapFactoryBean implements FactoryBean<LinkedHashMap<String, List<ConfigAttribute>>> {

    private SecurityResourceServiceImpl securityResourceService;
    private ResourceType resourceType;
    private LinkedHashMap<String, List<ConfigAttribute>> resourcesMap;

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public void setSecurityResourceService(SecurityResourceServiceImpl securityResourceService) {
        this.securityResourceService = securityResourceService;
    }

    public LinkedHashMap<String, List<ConfigAttribute>> getObject() {
        if (resourcesMap == null) {
            if (resourceType == ResourceType.METHOD)
                resourcesMap = securityResourceService.getHttpMethodResourceList(ResourceType.METHOD);
            else if (resourceType == ResourceType.POINTCUT)
                resourcesMap = securityResourceService.getHttpMethodResourceList(ResourceType.POINTCUT);
        }
        return resourcesMap;
    }

    @SuppressWarnings("rawtypes")
    public Class<LinkedHashMap> getObjectType() {
        return LinkedHashMap.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
