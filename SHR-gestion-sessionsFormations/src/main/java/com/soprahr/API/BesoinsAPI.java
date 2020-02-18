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
import com.soprahr.Services.BesoinServices;
import com.soprahr.models.Besoin;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/besoins")
public class BesoinsAPI {

	@Autowired
	public BesoinServices service;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addBesoin(@RequestBody Besoin besoin) {
		return service.addBesoin(besoin);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllBesoins() {
		return service.getAllBesoins();
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteBesoin(@Param(value = "id") int id) {
		return service.deleteBesoin(id);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getBesoinById(@PathParam(value = "id") int id) {
		return service.getBesoinById(id);
	}
}
