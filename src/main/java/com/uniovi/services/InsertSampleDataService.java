package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;

	@PostConstruct
	public void init() {
		User user1 = new User("Peter@gmail.com", "Pedro", "Díaz");
		user1.setPassword("123456");
		usersService.addUser(user1);
		User user2 = new User("Pablo@gmail.com", "Pablo", "Pérez");
		user2.setPassword("123456");
		usersService.addUser(user2);
		User user3 = new User("Marta@gmail.com", "Marta", "Sánchez");
		user3.setPassword("123456");
		usersService.addUser(user3);
		User user4 = new User("Maria@gmail.com", "María", "Fernández");
		user4.setPassword("123456");
		usersService.addUser(user4);
		User user5 = new User("Dromir@gmail.com", "David", "Ferreiro");
		user5.setPassword("123456");
		usersService.addUser(user5);
	}
}
