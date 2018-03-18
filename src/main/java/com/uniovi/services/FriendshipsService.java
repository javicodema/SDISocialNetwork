package com.uniovi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(FriendshipsService.class);

	public void addFriendship(FriendshipRequest fr) {
		logger.info(
				"Added friendship request from:" + fr.getSender().getEmail() + " to:" + fr.getReceiver().getEmail());
		frRepository.save(fr);
	}

	public Page<FriendshipRequest> findByUser(User user, Pageable pageable) {
		return frRepository.searchByUser(pageable, user);
	}

	public void formalize(User sender, User receiver) {
		FriendshipRequest fr = frRepository.findByUsers(sender, receiver);
		logger.info("Added friendship between:" + fr.getSender().getEmail() + " and:" + fr.getReceiver().getEmail());
		fr.accept();
		frRepository.delete(fr);
		FriendshipRequest reversefr = frRepository.findByUsers(receiver, sender);
		if (reversefr != null)
			frRepository.delete(reversefr);
	}
}
