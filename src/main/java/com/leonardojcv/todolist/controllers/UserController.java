package com.leonardojcv.todolist.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardojcv.todolist.entities.User;

@RestController
@RequestMapping(value="/users")
public class UserController {

	@PostMapping
	public void create(@RequestBody User user) {
		System.out.println(user.getName());
	}
}
