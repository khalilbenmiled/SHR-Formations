package com.soprahr.API;

import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soprahr.Services.ModuleServices;
import com.soprahr.models.Module;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/modules")
public class ModuleAPI {

	@Autowired
	public ModuleServices service;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addModule(@RequestBody Module module) {
		return service.addModule(module);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllModules() {
		return service.getAllModules();
	}

	@DeleteMapping(value="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteModule(@PathVariable(value = "id") int id) {
		return service.deleteModule(id);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getModuleById(@PathParam(value = "id") int id) {
		return service.getModuleById(id);
	}
	

}
