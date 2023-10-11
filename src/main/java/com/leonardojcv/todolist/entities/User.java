package com.leonardojcv.todolist.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="tb_users")
public class User {

	@Id
	@GeneratedValue(generator="UUID")
	private UUID id;
	
	private String name;
	
	@Column(unique=true)
	private String username;
	private String password;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
}
