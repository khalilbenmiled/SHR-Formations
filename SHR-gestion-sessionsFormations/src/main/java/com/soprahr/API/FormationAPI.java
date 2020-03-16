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

import com.soprahr.Services.FormationServices;
import com.soprahr.models.Formation;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value ="/formations")
public class FormationAPI {

	@Autowired
	public FormationServices service;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addFormation(@RequestBody Formation formation) {
		return service.addFormation(formation);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllFormations() {
		return service.getAllFormations();
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteFormation(@Param(value = "id") int id) {
		return service.deleteFormation(id);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getFormationById(@PathParam(value = "id") int id) {
		return service.getFormationById(id);
	}
}
