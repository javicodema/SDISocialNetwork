package com.uniovi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.services.PostsService;
import com.uniovi.services.UsersService;

@Controller
public class PostsController {

	@Autowired
	PostsService postsService;
	@Autowired
	UsersService userService;

	@RequestMapping("/post/list")
	public String getListado(Model model, Pageable pageable) {
		Page<Post> post = new PageImpl<Post>(new LinkedList<Post>());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = userService.getUserByEmail(email);
		post = postsService.getPostsByUser(pageable,activeUser);
		model.addAttribute("postsList", post.getContent());
		model.addAttribute("page", post);
		return "post/list";
	}

	@RequestMapping("/post/list/{id}")
	public String getListado(Model model, Pageable pageable, @PathVariable Long id) {
		Page<Post> post = new PageImpl<Post>(new LinkedList<Post>());
		post = postsService.getPostsByUser(pageable, userService.getUser(id));
		model.addAttribute("postsList", post.getContent());
		model.addAttribute("page", post);
		return "post/list";
	}

	@RequestMapping(value = "/post/add")
	public String getPost(Model model, Pageable pageable) {
		model.addAttribute("postsList", postsService.getPosts(pageable));
		return "post/add";
	}

	@RequestMapping(value = "/post/add", method = RequestMethod.POST)
	public String setPost(@RequestParam String title, @RequestParam String message,
			@RequestParam("imagen") MultipartFile informe) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = userService.getUserByEmail(email);
		Post post = new Post(title, message, activeUser);
		postsService.addPost(post);
		if (!informe.isEmpty()) {
			try {
				String fileName = informe.getOriginalFilename();
				String[] auxis = fileName.split("\\.");
				fileName = String.valueOf(post.getId()) + "." + auxis[1];
				InputStream is = informe.getInputStream();
				Files.copy(is, Paths.get("src/main/resources/static/posts/" + fileName),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
				return "redirect:/post/add";
			}
		}
		return "redirect:/post/list";
	}

}
