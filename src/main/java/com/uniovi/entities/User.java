package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class User {

	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String email;
	private String name;
	private String lastName;
	private String role;
	private String password;
	@Transient
	private String passwordConfirm;

	@JoinTable(name = "friendship", joinColumns = {
			@JoinColumn(name = "UserA", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "UserB", referencedColumnName = "id", nullable = false) })
	@ManyToMany
	private Set<User> friends;

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private Set<FriendshipRequest> outRequests = new HashSet<FriendshipRequest>();

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
	private Set<FriendshipRequest> inRequests = new HashSet<FriendshipRequest>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Post> posts;

	public User(String email, String name, String lastName) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
	}

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<Post> Post() {
		return posts;
	}

	public String getFullName() {
		return this.name + " " + this.lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<User> getFriends() {
		return friends;
	}

	public boolean existsRequest(User u) {
		for (FriendshipRequest f : outRequests)
			if (f.getReceiver() == u)
				return true;
		return false;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void addFriend(User u) {
		this.friends.add(u);
	}

	public void addSentRequest(FriendshipRequest r) {
		this.outRequests.add(r);
	}

	public void addReceivedRequest(FriendshipRequest r) {
		this.inRequests.add(r);
	}

	public void deleteSentRequest(FriendshipRequest r) {
		this.outRequests.remove(r);
	}

	public void deleteReceivedRequest(FriendshipRequest r) {
		this.inRequests.remove(r);
	}

	public void deleteAllFriends() {
		friends.clear();
	}

	public void deleteFriend(User actual) {
		friends.remove(actual);
	}
}
