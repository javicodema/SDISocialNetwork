package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.FriendshipRequest;
import com.uniovi.entities.User;

public interface FriendshipsRepository extends CrudRepository<FriendshipRequest, Long> {
	@Query("SELECT f FROM FriendshipRequest f WHERE f.receiver=?1")
	Page<FriendshipRequest> searchByUser(Pageable pageable, User user);

	Page<FriendshipRequest> findAll(Pageable pageable);

	@Query("SELECT f FROM FriendshipRequest f WHERE f.sender=?1 AND f.receiver=?2")
	FriendshipRequest findByUsers(User sender, User receiver);
}
