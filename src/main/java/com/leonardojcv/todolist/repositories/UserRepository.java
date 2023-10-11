package com.leonardojcv.todolist.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leonardojcv.todolist.entities.User;

public interface UserRepository extends JpaRepository<User, UUID>{
	User findByUsername(String username);
}
