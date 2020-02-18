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
import com.soprahr.Services.ThemeServices;
import com.soprahr.models.Theme;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/themes")
public class ThemeAPI {

	@Autowired
	public ThemeServices service;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addTheme(@RequestBody Theme theme) {
		return service.addTheme(theme);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllThemes() {
		return service.getAllThemes();
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteTheme(@Param(value = "id") int id) {
		return service.deleteTheme(id);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getThemeById(@PathParam(value = "id") int id) {
		return service.getThemeById(id);
	}
}
