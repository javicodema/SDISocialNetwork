package com.uniovi.entities;

import java.util.Date;

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
	private String title;
	private String message;
	private Date date;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Post() {
	}

	public Post(String title, String message, User user) {
		super();
		this.title = title;
		this.message = message;
		this.user = user;
		date = new Date();
	}

	public String getMessage() {
		return message;
	}

	public long getId() {
		return id;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
