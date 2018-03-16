package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE (LOWER(u.email) LIKE LOWER(?1) OR LOWER(u.name) LIKE LOWER(?1)) AND u<>?2")
	Page<User> searchByEmailAndName(Pageable pageable, String seachtext, User useractual);

	@Query("SELECT u FROM User u WHERE u<>?1")
	Page<User> findAll(Pageable pageable, User useractual);

	@Query("SELECT u.friends FROM User u WHERE u=?1")
	Page<User> findFriends(Pageable pageable, User useractual);
}
