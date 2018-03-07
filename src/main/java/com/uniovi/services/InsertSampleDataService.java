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
		User user6 = new User("Javier@gmail.com", "Javier", "Díez");
		user6.setPassword("123456");
		usersService.addUser(user6);
		User user7 = new User("Alejandro@gmail.com", "Alejandro", "González");
		user7.setPassword("123456");
		usersService.addUser(user7);
		User user8 = new User("Daniel@gmail.com", "Daniel", "Martín");
		user8.setPassword("123456");
		usersService.addUser(user8);
		User user9 = new User("Julio@gmail.com", "Julio", "Hernánez");
		user9.setPassword("123456");
		usersService.addUser(user9);
		User user10 = new User("Miguel@gmail.com", "Miguel", "Sánchez");
		user10.setPassword("123456");
		usersService.addUser(user10);
		User user11 = new User("Núñez@gmail.com", "Edward", "Núñez");
		user11.setPassword("123456");
		usersService.addUser(user11);

	}
}
