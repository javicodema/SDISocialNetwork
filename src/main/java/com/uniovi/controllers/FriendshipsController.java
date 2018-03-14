package com.uniovi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FriendshipsController {
	@RequestMapping("/requests/received")
	public String getList() {
		return "requests/received";
	}

	@RequestMapping("/requests/add/{id}")
	public String setRequest(@PathVariable Long id) {
		return "redirect:/user/list";
	}
}
