package com.pch777.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pch777.model.UserEntity;
import com.pch777.repository.UserEntityRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class UserEntityController {

	private UserEntityRepository userEntityRepository;

	@GetMapping("/users")
	public List<UserEntity> getUsers() {
		return userEntityRepository.findAll();
	}

	@PostMapping("/users")
	public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserEntity user) {
		userEntityRepository.save(user);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequestUri()
				.path("/" + user.getId()
								.toString())
								.build()
				.toUri();
		return ResponseEntity.created(uri).build();
	}
}
