package com.namolog.message.controller;


import com.namolog.message.common.AccountValidator;
import com.namolog.message.domain.NmAccount;
import com.namolog.message.dto.AccountDto;
import com.namolog.message.security.token.AjaxAuthenticationToken;
import com.namolog.message.service.AccountService;
import com.namolog.message.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private final AccountValidator accountValidator;
	private final AccountService accountService;
	private final PasswordEncoder passwordEncoder;

	@GetMapping(value="/login")
	public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception, Model model){
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "login";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null){
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/login";
	}

	@GetMapping(value="/register")
	public String register(AccountDto accountDto, HttpSession session, Model model){
		if (session.getAttribute("di") != null)
			accountDto.setDi(session.getAttribute("di").toString());
		if (session.getAttribute("name") != null)
			accountDto.setName(session.getAttribute("name").toString());
		if (session.getAttribute("mobile") != null)
			accountDto.setMobile(session.getAttribute("mobile").toString());
		model.addAttribute("accountDto", accountDto);
		return "register";
	}

	@PostMapping("/register")
	public String createUser(@Valid AccountDto accountDto, Errors errors) {
		//model.addAttribute("accountDto", accountDto);
		accountValidator.validate(accountDto, errors);
		if (errors.hasErrors())
			return "register";

		ModelMapper modelMapper = new ModelMapper();
		NmAccount account = modelMapper.map(accountDto, NmAccount.class);
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		accountService.createUser(account);
		return "redirect:/login";
	}

	@GetMapping(value="/denied")
	public String accessDenied(@RequestParam(value = "exception", required = false) String exception,
							   Principal principal, Model model) throws Exception {
		NmAccount nmAccount = null;
		if (principal instanceof UsernamePasswordAuthenticationToken) {
			nmAccount = (NmAccount) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
		}
		else if (principal instanceof AjaxAuthenticationToken){
			nmAccount = (NmAccount) ((AjaxAuthenticationToken) principal).getPrincipal();
		}

		model.addAttribute("username", nmAccount.getUsername());
		model.addAttribute("exception", exception);
		return "/denied";
	}
}
