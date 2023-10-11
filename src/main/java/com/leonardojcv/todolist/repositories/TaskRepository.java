package com.leonardojcv.todolist.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leonardojcv.todolist.entities.Task;

public interface TaskRepository extends JpaRepository<Task, UUID>{

}
