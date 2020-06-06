package com.namolog.message.controller.admin;


import com.namolog.message.domain.NmResource;
import com.namolog.message.domain.NmRole;
import com.namolog.message.domain.NmRoleResource;
import com.namolog.message.dto.ResourceDto;
import com.namolog.message.repository.RoleRepository;
import com.namolog.message.repository.RoleResourceRepository;
import com.namolog.message.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.namolog.message.service.MethodSecurityService;
import com.namolog.message.service.ResourceService;
import com.namolog.message.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.namolog.message.security.common.CommonUtil.existClassMethod;

@Controller
@RequiredArgsConstructor
public class ResourcesController {
	
	private final ResourceService resourceService;
	private final RoleRepository roleRepository;
	private final RoleResourceRepository roleResourceRepository;
	private final RoleService roleService;
	private final MethodSecurityService methodSecurityService;
	private final UrlFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

	@GetMapping(value="/admin/resource")
	public String getResources(Model model) throws Exception {

		List<NmResource> nmResourceList = resourceService.getResourceList();
		model.addAttribute("resourceList", nmResourceList);

		return "admin/resource/list";
	}

	@PostMapping(value="/admin/resource")
	public String createResources(ResourceDto resourceDto) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		NmRole nmRole = roleRepository.findByName(resourceDto.getRoleName())
				.orElseThrow(() -> new NoSuchElementException("Cannot find role : " + resourceDto.getRoleName()));
		NmResource nmResource = modelMapper.map(resourceDto, NmResource.class);
		resourceService.saveResource(nmResource);

		List<NmRoleResource> nmRoleResourceList = roleResourceRepository.findByResourceId(nmResource.getId());
		for (NmRoleResource nmRoleResource : nmRoleResourceList)
			roleResourceRepository.delete(nmRoleResource);
		NmRoleResource nmRoleResource = new NmRoleResource(nmRole, nmResource);
		roleResourceRepository.save(nmRoleResource);

		if ("URL".equals(resourceDto.getResourceType()))
			filterInvocationSecurityMetadataSource.reload();
		else if ("METHOD".equals(resourceDto.getResourceType()))
			// class 가 있는지 체크
			if (existClassMethod(resourceDto.getName()))
				methodSecurityService.addMethodSecured(resourceDto.getName(), resourceDto.getRoleName());
		else {
			methodSecurityService.addMethodSecured(resourceDto.getName(), resourceDto.getRoleName());
		}

		return "redirect:/admin/resource";
	}

	@GetMapping(value="/admin/resource/register")
	public String viewRoles(Model model) throws Exception {

		List<NmRole> nmRoleList = roleService.getRoles();
		model.addAttribute("roleList", nmRoleList);

		ResourceDto resourceDto = new ResourceDto();
		nmRoleList = new ArrayList<>();
		nmRoleList.add(new NmRole());
		resourceDto.setRoleList(nmRoleList);
		model.addAttribute("resource", resourceDto);

		return "admin/resource/detail";
	}

	@GetMapping(value="/admin/resource/{id}")
	public String getResource(@PathVariable Integer id, Model model) throws Exception {
		List<NmRole> nmRoleList = roleService.getRoles();
        model.addAttribute("roleList", nmRoleList);
		NmResource nmResource = resourceService.getResource(id);
		ResourceDto resourceDto = ResourceDto.builder()
				.id(nmResource.getId())
				.name(nmResource.getName())
				.httpMethod(nmResource.getHttpMethod())
				.resourceType(nmResource.getResourceType().name())
				.orderNum(nmResource.getOrderNum())
				.roleList(new ArrayList<>())
				.build();
		List<NmRoleResource> nmRoleResourceList = roleResourceRepository.findByResourceId(nmResource.getId());
		for (NmRoleResource nmRoleResource : nmRoleResourceList)
			resourceDto.getRoleList().add(nmRoleResource.getRole());

		model.addAttribute("resource", resourceDto);
		return "admin/resource/detail";
	}

	@GetMapping(value="/admin/resource/delete/{id}")
	public String removeResource(@PathVariable Integer id, Model model) throws Exception {
		NmResource nmResource = resourceService.getResource(id);
		resourceService.deleteResource(id);

		if ("URL".equals(nmResource.getResourceType().name()))
			filterInvocationSecurityMetadataSource.reload();
		else if ("METHOD".equals(nmResource.getResourceType().name()))
			// class 가 있는지 체크
			if (existClassMethod(nmResource.getName()))
				methodSecurityService.removeMethodSecured(nmResource.getName());
		else {
			methodSecurityService.removeMethodSecured(nmResource.getName());
		}

		return "redirect:/admin/resource";
	}
}
