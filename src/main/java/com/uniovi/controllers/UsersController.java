package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AdminLoginFormValidator;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private SignUpFormValidator signUpFormValidator;
	@Autowired
	private AdminLoginFormValidator admin;
	@Autowired
	private RolesService rolesService;
	private boolean correctSignIn = true;

	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText) {
		correctSignIn = true;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User useractual = usersService.getUserByEmail(auth.getName());
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUsersByEmailAndName(pageable, searchText, useractual);
		} else {
			users = usersService.getUsers(pageable, useractual);
		}
		model.addAttribute("actual", useractual);
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "user/list";
	}

	@RequestMapping(value = "/user/friends")
	public String getFriends(Principal principal, Model model, Pageable pageable) {
		User useractual = usersService.getUserByEmail(principal.getName());
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersService.getFriends(pageable, useractual);
		model.addAttribute("friendsList", users.getContent());
		model.addAttribute("page", users);
		return "user/friends";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result, Model model) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("errorMsg", correctSignIn);
		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

	@RequestMapping("/login/error")
	public String updateLogin(Model model) {
		correctSignIn = false;
		return "redirect:/login";
	}

	@RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	public String adminLogin(Model model) {
		model.addAttribute("user", new User());
		return "admin/login";
	}

	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public String adminLogin(@Validated User user, BindingResult result, Model model) {
		admin.validate(user, result);
		if (result.hasErrors()) {
			return "admin/login";
		}
		return "redirect:/admin/list";
	}

	@RequestMapping("/admin/delete/{id}")
	public String delete(@PathVariable Long id) {
		if (usersService.getUser(id) != null) {
			usersService.deleteFriends(id);
			usersService.deleteUser(id);
		}
		return "redirect:/admin/list";
	}

	@RequestMapping("/admin/list")
	public String getAdmListado(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User useractual = usersService.getUserByEmail(auth.getName());
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if ((searchText != null) && (!searchText.isEmpty())) {
			users = usersService.searchUsersByEmailAndName(pageable, searchText, useractual);
		} else {
			users = usersService.getUsers(pageable, useractual);
		}
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "admin/list";
	}

}
