package com.namolog.message.controller.admin;


import com.namolog.message.domain.NmAccount;
import com.namolog.message.domain.NmRole;
import com.namolog.message.dto.AccountDto;
import com.namolog.message.service.AccountService;
import com.namolog.message.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AccountController {
	
	private final AccountService accountService;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;

	@GetMapping(value = "/account")
	public String getUsers(Model model) throws Exception {
		List<NmAccount> nmAccountList = accountService.getUsers();
		model.addAttribute("accountList", nmAccountList);
		return "admin/account/list";
	}

	@GetMapping("/account/register")
	public String createUser() {
		return "admin/account/register";
	}

	@PostMapping("/account/register")
	public String createUser(@Valid AccountDto accountDto) {
		ModelMapper modelMapper = new ModelMapper();
		NmAccount account = modelMapper.map(accountDto, NmAccount.class);
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		accountService.createUser(account);
		return "redirect:/admin/account";
	}

	@GetMapping(value = "/account/{id}")
	public String getUser(@PathVariable Integer id, Model model) {
		AccountDto accountDto = accountService.getUser(id);
		List<NmRole> nmRoleList = roleService.getRoles();

		model.addAttribute("account", accountDto);
		model.addAttribute("roleList", nmRoleList);

		return "admin/account/detail";
	}

	@PostMapping(value = "/account")
	public String modifyUser(AccountDto accountDto) throws Exception {
		accountService.modifyUser(accountDto);
		return "redirect:/admin/account";
	}

	@GetMapping(value = "/account/delete/{id}")
	public String removeUser(@PathVariable Integer id, Model model) {

		accountService.deleteUser(id);

		return "redirect:/admin/accounts";
	}
}
