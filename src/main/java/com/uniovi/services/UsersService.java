package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	public void init() {
	}

	public Page<User> getUsers(Pageable pageable, User useractual) {
		Page<User> users = usersRepository.findAll(pageable, useractual);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findOne(id);
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public User getUserByEmail(String username) {
		return usersRepository.findByEmail(username);
	}

	public void deleteUser(Long id) {
		usersRepository.delete(id);
	}

	public Page<User> searchUsersByEmailAndName(Pageable pageable, String searchText, User useractual) {
		Page<User> users = new PageImpl<User>(new ArrayList<User>());
		searchText = "%" + searchText + "%";
		users = usersRepository.searchByEmailAndName(pageable, searchText, useractual);
		return users;
	}

	public Page<User> getFriends(Pageable pageable, User useractual) {
		return usersRepository.findFriends(pageable, useractual);
	}

	public void deleteFriends(Long id) {
		List<User> users = usersRepository.findFriendsDelete(id);
		User actual = usersRepository.findById(id);
		actual.deleteAllFriends();
		for(User u:users) {
			u.deleteFriend(actual);
		}
	}
}
