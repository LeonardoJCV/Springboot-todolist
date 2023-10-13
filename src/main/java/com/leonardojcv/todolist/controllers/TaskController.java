package com.leonardojcv.todolist.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardojcv.todolist.entities.Task;
import com.leonardojcv.todolist.repositories.TaskRepository;
import com.leonardojcv.todolist.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	
	@PostMapping
	public ResponseEntity create(@RequestBody Task task, HttpServletRequest request) {
		// Recuperação do parametro user.getId() no contrller (idUser)
		task.setIdUser((UUID)request.getAttribute("idUser"));
		// Validação da data
		// Se a data atual for maior que a data de incio da tarefa, não pode realizar a inserção
		// Ou se a data atual for depois da data de finalização da tarefa
		if (LocalDateTime.now().isAfter(task.getStartAt()) || LocalDateTime.now().isAfter(task.getEndAt())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("A data de início / data de termino deve ser maior que a data atual");
		}
		
		if (task.getStartAt().isAfter(task.getEndAt())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("A data de início deve ser  menor do que a data de termino");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(this.taskRepository.save(task));
	}
	
	// Retorna uma lista de tudo o que for relacionado ao usuario correspondente
	@GetMapping
	public List<Task> list(HttpServletRequest request) {
		return this.taskRepository.findByIdUser((UUID)request.getAttribute("idUser"));
	}
	
	// Método para atualização de uma tarefa
	@PutMapping("/{id}")
	public ResponseEntity update(@RequestBody Task task, @PathVariable UUID id, HttpServletRequest request) {
		var idUser = request.getAttribute("idUser");
		var tasks = this.taskRepository.findById(id).orElse(null);
		// Verificação de existencia da tarefa
		if (tasks == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Tarefa não encontrada");
		}
		// Verificação de usuario para alterção de tarefas
		if (!tasks.getIdUser().equals(idUser)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Usúario não tem permissão para alterar essa tarefa");
		}
		Utils.copyNonNullProperties(task, tasks);
		return ResponseEntity.ok().body(this.taskRepository.save(tasks));
	}
}
