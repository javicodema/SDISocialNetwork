package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.FriendshipRequest;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;
	@Autowired
	private FriendshipsService frService;
	@Autowired
	private RolesService rolesService;


	@PostConstruct
	public void init() {
		User user1 = new User("Peter@gmail.com", "Pedro", "Díaz");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user1);
		User user2 = new User("Pablo@gmail.com", "Pablo", "Pérez");
		user2.setPassword("123456");
		user2.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user2);
		User user3 = new User("Marta@gmail.com", "Marta", "Sánchez");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user3);
		User user4 = new User("Maria@gmail.com", "María", "Fernández");
		user4.setPassword("123456");
		user4.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user4);
		User user5 = new User("Dromir@gmail.com", "David", "Ferreiro");
		user5.setPassword("123456");
		user5.setRole(rolesService.getRoles()[1]);
		usersService.addUser(user5);
		User user6 = new User("Javier@gmail.com", "Javier", "Díez");
		user6.setPassword("123456");
		user6.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user6);
		User user7 = new User("Alejandro@gmail.com", "Alejandro", "González");
		user7.setPassword("123456");
		user7.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user7);
		User user8 = new User("Daniel@gmail.com", "Daniel", "Martín");
		user8.setPassword("123456");
		user8.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user8);
		User user9 = new User("Julio@gmail.com", "Julio", "Hernánez");
		user9.setPassword("123456");
		user9.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user9);
		User user10 = new User("Miguel@gmail.com", "Miguel", "Sánchez");
		user10.setPassword("123456");
		user10.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user10);
		User user11 = new User("Núñez@gmail.com", "Edward", "Núñez");
		user11.setPassword("123456");
		user11.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user11);
		FriendshipRequest fr1 = new FriendshipRequest(user1, user2);
		frService.addFriendship(fr1);
		FriendshipRequest fr2 = new FriendshipRequest(user1, user3);
		frService.addFriendship(fr2);
		FriendshipRequest fr3 = new FriendshipRequest(user1, user4);
		frService.addFriendship(fr3);
		FriendshipRequest fr4 = new FriendshipRequest(user2, user3);
		frService.addFriendship(fr4);
		FriendshipRequest fr5 = new FriendshipRequest(user2, user4);
		frService.addFriendship(fr5);
		FriendshipRequest fr6 = new FriendshipRequest(user3, user4);
		frService.addFriendship(fr6);
		FriendshipRequest fr7 = new FriendshipRequest(user4, user5);
		frService.addFriendship(fr7);
		FriendshipRequest fr8 = new FriendshipRequest(user6, user1);
		frService.addFriendship(fr8);
		FriendshipRequest fr9 = new FriendshipRequest(user6, user4);
		frService.addFriendship(fr9);
		FriendshipRequest fr10 = new FriendshipRequest(user7, user1);
		frService.addFriendship(fr10);
	}
}
