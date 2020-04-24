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

import com.soprahr.Services.ProjetServices;
import com.soprahr.model.Projet;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/projets")
public class ProjetAPI {

	@Autowired
	public ProjetServices service;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addProjet(@RequestBody Projet projet) {
		return service.addProjet(projet);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllProjets() {
		return service.getAllProjets();
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteProjet(@Param(value = "id") int id) {
		return service.deleteProjet(id);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getProjetById(@PathParam(value = "id") int id) {
		return service.getProjetById(id);
	}
	
	@PostMapping(value = "/byTL", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getProjetsByTL(@Param(value = "id") int id) {
		return service.getProjetByTL(id);
	}
	
	@PostMapping(value = "/byMG", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getProjetsByMG(@Param(value = "id") int id) {
		return service.getProjetByMG(id);
	}
	
}
