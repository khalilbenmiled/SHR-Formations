package com.soprahr.API;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.soprahr.Services.CollaborateurServices;
import net.minidev.json.JSONObject;


@RestController
@RequestMapping(value = "/collaborateurs")
public class CollaborateurAPI {
	
	
	@Autowired
	public CollaborateurServices service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getCollaborateurs() {
		return service.getAllCollaborateurs();
	}
	
	@PostMapping(value = "/byID", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getCollaborateurById(@Param(value = "id") int id) {
		return service.getCollaborateurById(id);
	}

	@PostMapping(value = "/ByTL", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject collaborateurByTL(@Param(value = "id") int id) {
		return service.getCollaborateurByTL(id);
	}
	
	
	
}
