package com.namolog.message.security.listener;

import com.namolog.message.domain.*;
import com.namolog.message.repository.*;
import com.namolog.message.security.common.ResourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Transactional
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountRepository accountRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final ResourceRepository resourceRepository;
    private final RoleResourceRepository roleResourceRepository;
    private final RoleHierarchyRepository roleHierarchyRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessIpRepository accessIpRepository;

    private boolean alreadySetup = false;
    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
//        if (alreadySetup)
//            return;
//
//        setupSecurityResources();
//        // 회사 아이피
//        setupAccessIpData("119.205.70.19");
//
//        alreadySetup = true;
    }

    private void setupSecurityResources() {
        NmRole adminNmRole = createRoleIfNotFound("ROLE_ADMIN", "시스템 관리자 권한");
        NmRole managerNmRole = createRoleIfNotFound("ROLE_MANAGER", "관리자 권한");
        NmRole userNmRole = createRoleIfNotFound("ROLE_USER", "사용자 권한");
        NmAccount adminNmAccount = createUserIfNotFound("admin", "namo1004", "dosa@namo.zone");
        createUserRoleIfNotFound(adminNmAccount, adminNmRole);

        NmResource adminNmResource = createResourceIfNotFound("/admin/**", "", ResourceType.URL);
        NmResource managerNmResource = createResourceIfNotFound("/manager/**", "", ResourceType.URL);
        NmResource userNmResource = createResourceIfNotFound("/user/**", "", ResourceType.URL);
        createRoleResourceIfNotFound(adminNmRole, adminNmResource);
        createRoleResourceIfNotFound(managerNmRole, managerNmResource);
        createRoleResourceIfNotFound(userNmRole, userNmResource);

        //Resource methodResource = createResourceIfNotFound("execution(public * com.namolog.message.*Service.pointcut*(..))", "", ResourceType.POINTCUT);

        createRoleHierarchyIfNotFound(managerNmRole, adminNmRole);
        createRoleHierarchyIfNotFound(userNmRole, managerNmRole);
    }

    public NmRole createRoleIfNotFound(String roleName, String roleDesc) {
        NmRole nmRole = roleRepository.findByName(roleName).orElse(null);
        if (nmRole == null) {
            nmRole = new NmRole(roleName, roleDesc);
        }
        return roleRepository.save(nmRole);
    }

    public NmAccount createUserIfNotFound(String userName, String password, String email) {
        NmAccount nmAccount = accountRepository.findByUsername(userName).orElse(null);
        if (nmAccount == null) {
            nmAccount = new NmAccount(userName, passwordEncoder.encode(password), email);
        }
        return accountRepository.save(nmAccount);
    }

    public NmAuthority createUserRoleIfNotFound(NmAccount nmAccount, NmRole nmRole) {
        NmAuthority nmAuthority = authorityRepository.findByAccountIdAndRoleId(nmAccount.getId(), nmRole.getId()).orElse(null);
        if (nmAuthority == null) {
            nmAuthority = new NmAuthority(nmAccount, nmRole);
        }
        return authorityRepository.save(nmAuthority);
    }

    public NmResource createResourceIfNotFound(String resourceName, String httpMethod, ResourceType resourceType) {
        NmResource nmResource = resourceRepository.findByNameAndHttpMethod(resourceName, httpMethod).orElse(null);

        if (nmResource == null) {
            nmResource = new NmResource(resourceName, httpMethod, resourceType, count.incrementAndGet());
        }
        return resourceRepository.save(nmResource);
    }

    public NmRoleResource createRoleResourceIfNotFound(NmRole nmRole, NmResource nmResource) {
        NmRoleResource nmRoleResource = roleResourceRepository.findByRoleIdAndResourceId(nmRole.getId(), nmResource.getId()).orElse(null);
        if (nmRoleResource == null) {
            nmRoleResource = new NmRoleResource(nmRole, nmResource);
        }
        return roleResourceRepository.save(nmRoleResource);
    }

    public void createRoleHierarchyIfNotFound(NmRole childNmRole, NmRole parentNmRole) {
        NmRoleHierarchy nmRoleHierarchy = roleHierarchyRepository.findByChild(parentNmRole.getName());
        if (nmRoleHierarchy == null) {
            nmRoleHierarchy = NmRoleHierarchy.builder()
                    .child(parentNmRole.getName())
                    .build();
        }
        NmRoleHierarchy parentNmRoleHierarchy = roleHierarchyRepository.save(nmRoleHierarchy);

        nmRoleHierarchy = roleHierarchyRepository.findByChild(childNmRole.getName());
        if (nmRoleHierarchy == null) {
            nmRoleHierarchy = NmRoleHierarchy.builder()
                    .child(childNmRole.getName())
                    .build();
        }

        NmRoleHierarchy childNmRoleHierarchy = roleHierarchyRepository.save(nmRoleHierarchy);
        childNmRoleHierarchy.setParent(parentNmRoleHierarchy);
    }

    private void setupAccessIpData(String ipAddress) {
        NmAccessIp byIpAddress = accessIpRepository.findByAddress(ipAddress);
        if (byIpAddress == null) {
            NmAccessIp nmAccessIp = NmAccessIp.builder()
                    .address(ipAddress)
                    .build();
            accessIpRepository.save(nmAccessIp);
        }
    }
}
