package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.repositories.PostsRepository;

@Service
public class PostsService {
	@Autowired
	private PostsRepository postsRepository;

	public Page<Post> getPosts(Pageable pageable) {
		Page<Post> users = postsRepository.findAll(pageable);
		return users;
	}

	public Post getPost(Long id) {
		return postsRepository.findOne(id);
	}

	public void addPost(Post post) {
		postsRepository.save(post);
	}

	public Page<Post> getPostsByUser(Pageable pageable, User user) {
		return postsRepository.searchByUser(pageable, user);
	}

	public void deletePost(Long id) {
		postsRepository.delete(id);
	}

}
