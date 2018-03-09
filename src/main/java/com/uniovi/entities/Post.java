package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Post {

	@Id
	@GeneratedValue
	private long id;
	private String message;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	
	public Post() {
	}

	
	

	public Post(String message, User user) {
		super();
		this.message = message;
		this.user = user;
	}

	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
}
