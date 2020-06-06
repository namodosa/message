package com.namolog.message.security.config;

import com.namolog.message.security.common.ResourceType;
import com.namolog.message.security.factory.MethodResourcesMapFactoryBean;
import com.namolog.message.security.interceptor.CustomMethodSecurityInterceptor;
import com.namolog.message.security.processor.ProtectPointcutPostProcessor;
import com.namolog.message.service.impl.SecurityResourceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.RunAsManager;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    private final SecurityResourceServiceImpl securityResourceService;

    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return mapBasedMethodSecurityMetadataSource();
    }

    @Bean
    public MapBasedMethodSecurityMetadataSource mapBasedMethodSecurityMetadataSource() {
        return new MapBasedMethodSecurityMetadataSource(methodResourcesMapFactoryBean().getObject());
    }

    @Bean
    public MethodResourcesMapFactoryBean methodResourcesMapFactoryBean(){
        MethodResourcesMapFactoryBean methodResourcesMapFactoryBean = new MethodResourcesMapFactoryBean();
        methodResourcesMapFactoryBean.setSecurityResourceService(securityResourceService);
        methodResourcesMapFactoryBean.setResourceType(ResourceType.METHOD);
        return methodResourcesMapFactoryBean;
    }

    @Bean
//    @Profile("pointcut")
    public MethodResourcesMapFactoryBean pointcutResourcesMapFactoryBean() {
        MethodResourcesMapFactoryBean methodResourcesMapFactoryBean = new MethodResourcesMapFactoryBean();
        methodResourcesMapFactoryBean.setSecurityResourceService(securityResourceService);
        methodResourcesMapFactoryBean.setResourceType(ResourceType.POINTCUT);
        return methodResourcesMapFactoryBean;
    }

    @Bean
//    @Profile("pointcut")
    public ProtectPointcutPostProcessor protectPointcutPostProcessor() {
        ProtectPointcutPostProcessor protectPointcutPostProcessor = new ProtectPointcutPostProcessor(mapBasedMethodSecurityMetadataSource());
        protectPointcutPostProcessor.setPointcutMap(pointcutResourcesMapFactoryBean().getObject());
        return protectPointcutPostProcessor;
    }

    @Bean
    public CustomMethodSecurityInterceptor customMethodSecurityInterceptor(MapBasedMethodSecurityMetadataSource methodSecurityMetadataSource) {
        CustomMethodSecurityInterceptor customMethodSecurityInterceptor =  new CustomMethodSecurityInterceptor();
        customMethodSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
        customMethodSecurityInterceptor.setAfterInvocationManager(afterInvocationManager());
        customMethodSecurityInterceptor.setSecurityMetadataSource(methodSecurityMetadataSource);
        RunAsManager runAsManager = runAsManager();
        if (runAsManager != null) {
            customMethodSecurityInterceptor.setRunAsManager(runAsManager);
        }

        return customMethodSecurityInterceptor;
    }

//    // ProtectPointcutPostProcessor 가 final class 라서 상속을 받을 수가 없음.
//    // 리플렉션 방식으로 접금. xml 방식이 아니라서 처리해 줘야 됨
//    @Bean
////    @Profile("pointcut")
//    BeanPostProcessor protectPointcutPostProcessor() throws Exception {
//        Class<?> clazz = Class.forName("org.springframework.security.config.method.ProtectPointcutPostProcessor");
//        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(MapBasedMethodSecurityMetadataSource.class);
//        declaredConstructor.setAccessible(true);
//        Object instance = declaredConstructor.newInstance(mapBasedMethodSecurityMetadataSource());
//        Method setPointcutMap = instance.getClass().getHttpMethod("setPointcutMap", Map.class);
//        setPointcutMap.setAccessible(true);
//        setPointcutMap.invoke(instance, pointcutResourcesMapFactoryBean().getObject());
//
//        return (BeanPostProcessor)instance;
//    }


}
