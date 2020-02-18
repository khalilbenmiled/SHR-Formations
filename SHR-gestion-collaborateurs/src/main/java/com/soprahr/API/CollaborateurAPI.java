package com.soprahr.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprahr.Repository.CollaborateurRepository;
import com.soprahr.models.Collaborateur;

@RestController
@RequestMapping(value = "/api")
public class CollaborateurAPI {
	
	
	@Autowired
	public CollaborateurRepository repository;
	
	@GetMapping
	public List<Collaborateur> getAll(){
		return repository.findAll();
	}

}
