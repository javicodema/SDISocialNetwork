package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.FriendshipRequest;
import com.uniovi.entities.User;
import com.uniovi.services.FriendshipsService;
import com.uniovi.services.UsersService;

@Controller
public class FriendshipsController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private FriendshipsService frService;

	@RequestMapping("/requests/received")
	public String getList() {
		return "requests/received";
	}

	@RequestMapping("/requests/add/{id}")
	public String setRequest(@PathVariable Long id, Principal principal) {
		User receiver = usersService.getUser(id);
		User sender = usersService.getUserByEmail(principal.getName());
		FriendshipRequest fr = new FriendshipRequest(sender, receiver);
		frService.addFriendship(fr);
		return "redirect:/user/list";
	}
}
