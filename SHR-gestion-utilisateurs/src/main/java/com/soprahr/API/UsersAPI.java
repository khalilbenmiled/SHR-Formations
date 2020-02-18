package com.soprahr.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprahr.models.User;
import com.soprahr.repository.UsersRepository;
import com.soprahr.services.UsersService;

import net.minidev.json.JSONObject;

@RestController
public class UsersAPI {

	@Autowired
	public UsersService service;
	@Autowired
	public UsersRepository repository;

	@GetMapping(value = "/test")
	public User sayHello() {
		return repository.verifyPassword("admin", "admin");
	}

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject logIn(@Param(value = "email") String email, @Param(value = "password") String password) {
		return service.logIn(email, password);				
	}
	

	

}
