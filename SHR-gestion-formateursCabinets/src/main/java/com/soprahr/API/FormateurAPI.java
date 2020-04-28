package com.soprahr.API;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soprahr.Services.FormateurServices;
import com.soprahr.models.Formateur;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/formateurs")
public class FormateurAPI {

	@Autowired
	public FormateurServices service;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addFormateur(@RequestBody Formateur formateur) {
		return service.addFormateur(formateur);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllFormateurs() {
		return service.getAllFormateurs();
	}
	
	@PostMapping(value="/delete" , produces = MediaType.APPLICATION_JSON_VALUE ,   consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject deleteFormateur(@Param(value = "id") int id) {
		return service.deleteFormateurs(id);
	}
	
	@PostMapping(value="/update" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject updateFormateur(@RequestBody Formateur formateur) {
		return service.updateFormateur(formateur);
	}
	
	
	@GetMapping(value ="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getFormateurById(@PathParam(value = "id") int id) {
		return service.getFormateurById(id);
	}
	
	@GetMapping(value = "/byBU" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getFormateurByBU(@Param (value ="bu") int bu) {
		return service.getFormateurByBU(bu);
	}
	
	@GetMapping(value = "/byExpertise" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getFormateurByExpertise(@Param (value ="expertise") int expertise) {
		return service.getFormateurByExpertise(expertise);
	}
	
}
