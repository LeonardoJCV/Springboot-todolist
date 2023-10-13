package com.leonardojcv.todolist.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardojcv.todolist.entities.Task;
import com.leonardojcv.todolist.repositories.TaskRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	
	@PostMapping
	public Task create(@RequestBody Task task, HttpServletRequest request) {
		// Recuperação do parametro user.getId() no contrller (idUser)
		task.setIdUser((UUID)request.getAttribute("idUser"));
		
		return this.taskRepository.save(task);
	}
}
