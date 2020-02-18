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
import com.soprahr.Services.ProgrammeServices;
import com.soprahr.models.Programme;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping( value = "/programmes")
public class ProgrammeAPI {

	@Autowired
	public ProgrammeServices service;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addProgramme(@RequestBody Programme programme) {
		return service.addProgramme(programme);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllProgrammes() {
		return service.getAllProgrammes();
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteProgramme(@Param(value = "id") int id) {
		return service.deleteProgramme(id);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getProgrammeById(@PathParam(value = "id") int id) {
		return service.getProgrammeById(id);
	}
}
