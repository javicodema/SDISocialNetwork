package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.FriendshipRequest;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendshipsRepository;

@Service
public class FriendshipsService {
	@Autowired
	FriendshipsRepository frRepository;

	public void addFriendship(FriendshipRequest fr) {
		frRepository.save(fr);
	}

	public Page<FriendshipRequest> findByUser(User user, Pageable pageable) {
		return frRepository.searchByUser(pageable, user);
	}

	public void formalize(User sender, User receiver) {
		FriendshipRequest fr = frRepository.findByUsers(sender, receiver);
		fr.accept();
		frRepository.delete(fr);
		FriendshipRequest reversefr = frRepository.findByUsers(receiver, sender);
		if (reversefr != null)
			frRepository.delete(reversefr);
	}
}
