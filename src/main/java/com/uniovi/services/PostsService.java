package com.uniovi.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.repositories.PostsRepository;

@Service
public class PostsService {
	@Autowired
	private PostsRepository postsRepository;
	private static final Logger logger = LoggerFactory.getLogger(PostsService.class);

	public Page<Post> getPosts(Pageable pageable) {
		Page<Post> users = postsRepository.findAll(pageable);
		return users;
	}

	public Post getPost(Long id) {
		return postsRepository.findOne(id);
	}

	public void addPost(Post post) {
		logger.info("Post added by:" + post.getUser().getEmail() + " with title:" + post.getTitle());
		postsRepository.save(post);
	}

	public Page<Post> getPostsByUser(Pageable pageable, User user) {
		return postsRepository.searchByUser(pageable, user);
	}

	public void saveImage(MultipartFile informe, Post post) throws IOException {
		String fileName = informe.getOriginalFilename();
		fileName = "P-" + String.valueOf(post.getId()) + "." + "jpg";
		InputStream is = informe.getInputStream();
		Files.copy(is, Paths.get("src/main/resources/static/fotossubidas/" + fileName),
				StandardCopyOption.REPLACE_EXISTING);
	}

}
