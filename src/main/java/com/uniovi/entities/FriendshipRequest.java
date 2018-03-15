package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FriendshipRequest {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User sender;
	@ManyToOne
	@JoinColumn(name = "receiver_id")
	private User receiver;

	public FriendshipRequest() {

	}

	public FriendshipRequest(User send, User receive) {
		super();
		this.sender = send;
		this.receiver = receive;
		this.sender.addSentRequest(this);
		this.receiver.addReceivedRequest(this);
	}

	public void accept() {
		this.sender.addFriend(receiver);
		this.receiver.addFriend(sender);
		this.sender.deleteSentRequest(this);
		this.receiver.deleteReceivedRequest(this);
	}

	public User getReceiver() {
		return receiver;
	}

	public User getSender() {
		return sender;
	}
}
