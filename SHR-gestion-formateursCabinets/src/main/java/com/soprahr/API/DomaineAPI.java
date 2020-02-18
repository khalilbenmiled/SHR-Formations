package com.soprahr.API;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soprahr.Services.DomaineServices;
import com.soprahr.models.Domaine;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/domaine") 
public class DomaineAPI {

	@Autowired
	public DomaineServices service;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addDomaine(@RequestBody Domaine domaine) {
		return service.addDomaine(domaine);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllDomaines() {
		return service.getAllDomaines();
	}
	
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteDomaine(@Param(value = "id") int id) {
		return service.deleteDomaine(id);
	}
	
	@GetMapping(value ="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getDomaineById(@PathParam(value = "id") int id) {
		return service.getDomaineById(id);
	}
}
