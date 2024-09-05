package com.manoj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	
	public User findByEmail(String username);
}
