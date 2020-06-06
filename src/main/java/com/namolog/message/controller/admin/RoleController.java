package com.namolog.message.controller.admin;

import com.namolog.message.domain.NmRole;
import com.namolog.message.dto.RoleDto;
import com.namolog.message.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoleController {

	private final RoleService roleService;

	@GetMapping(value="/admin/role")
	public String getRole(Model model) throws Exception {

		List<NmRole> nmRoleList = roleService.getRoles();
		model.addAttribute("roleList", nmRoleList);

		return "admin/role/list";
	}

	@GetMapping(value="/admin/role/register")
	public String viewRole(Model model) throws Exception {

		RoleDto role = new RoleDto();
		model.addAttribute("role", role);

		return "admin/role/detail";
	}

	@PostMapping(value="/admin/role")
	public String createRole(RoleDto roleDto) throws Exception {

		ModelMapper modelMapper = new ModelMapper();
		NmRole nmRole = modelMapper.map(roleDto, NmRole.class);
		roleService.createRole(nmRole);

		return "redirect:/admin/role";
	}

	@GetMapping(value="/admin/role/{id}")
	public String getRole(@PathVariable Integer id, Model model) throws Exception {

		NmRole nmRole = roleService.getRole(id);

		ModelMapper modelMapper = new ModelMapper();
		RoleDto roleDto = modelMapper.map(nmRole, RoleDto.class);
		model.addAttribute("role", roleDto);

		return "admin/role/detail";
	}

	@GetMapping(value="/admin/role/delete/{id}")
	public String removeResource(@PathVariable Integer id, Model model) throws Exception {
		roleService.deleteRole(id);
		return "redirect:/admin/resource";
	}
}
