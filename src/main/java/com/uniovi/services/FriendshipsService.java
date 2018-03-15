package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.FriendshipRequest;
import com.uniovi.repositories.FriendshipsRepository;

@Service
public class FriendshipsService {
	@Autowired
	FriendshipsRepository frRepository;

	public void addFriendship(FriendshipRequest fr) {
		frRepository.save(fr);
	}
}
