package com.leonardojcv.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardojcv.todolist.entities.User;
import com.leonardojcv.todolist.repositories.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping(value="/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
		
	@PostMapping
	public ResponseEntity create(@RequestBody User user) {
		if (this.userRepository.findByUsername(user.getUsername()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usúario já existe!");
		}
		user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
		return ResponseEntity.status(HttpStatus.OK).body(this.userRepository.save(user));
	}
}
