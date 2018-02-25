package com.uniovi.repositories;

import com.uniovi.entities.*;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
}
