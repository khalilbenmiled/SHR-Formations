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

import com.soprahr.Services.SessionServices;
import com.soprahr.models.Session;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/sessions")
public class SessionAPI {

	@Autowired
	public SessionServices service;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addSession(@RequestBody Session session) {
		return service.addSession(session);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllSessions() {
		return service.getAllSession();
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteSession(@Param(value = "id") int id) {
		return service.deleteSession(id);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getSessionById(@PathParam(value = "id") int id) {
		return service.getSessionById(id);
	}
	
	@PostMapping(value = "/byFormation" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getSessionByFormation(@Param(value = "idFormation") int idFormation) {
		return service.getSessionByFormation(idFormation);
	}
	
	@PostMapping(value = "/byQuarter" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getSessionByQuarter(@Param(value = "quarter") int quarter) {
		return service.getSessionByQuarter(quarter);
	}
	
	
}
