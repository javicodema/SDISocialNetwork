package com.uniovi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FriendshipsController {
	@RequestMapping("/requests/received")
	public String getList() {
		return "index";
	}
}
