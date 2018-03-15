package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String getList(Principal principal, Pageable pageable, Model model) {
		Page<FriendshipRequest> requests = new PageImpl<FriendshipRequest>(new LinkedList<FriendshipRequest>());
		User activeUser = usersService.getUserByEmail(principal.getName());
		requests = frService.findByUser(activeUser, pageable);
		model.addAttribute("requestsList", requests.getContent());
		model.addAttribute("page", requests);
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

	@RequestMapping("/requests/accept/{id}")
	public String acceptRequest(Model model, Pageable pageable, @PathVariable Long id, Principal principal) {
		User sender = usersService.getUser(id);
		User receiver = usersService.getUserByEmail(principal.getName());
		frService.formalize(sender, receiver);
		Page<FriendshipRequest> requests = new PageImpl<FriendshipRequest>(new LinkedList<FriendshipRequest>());
		User activeUser = usersService.getUserByEmail(principal.getName());
		requests = frService.findByUser(activeUser, pageable);
		model.addAttribute("requestsList", requests.getContent());
		model.addAttribute("page", requests);
		return "requests/received";
	}
}
