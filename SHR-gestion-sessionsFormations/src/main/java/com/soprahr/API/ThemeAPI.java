package com.soprahr.API;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@PostMapping(value = "/modifier" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject modifierTheme(@RequestBody Theme theme) {
		return service.modifierTheme(theme);
	}

	@DeleteMapping(value="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteTheme(@PathVariable(value = "id") int id) {
		return service.deleteTheme(id);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getThemeById(@PathParam(value = "id") int id) {
		return service.getThemeById(id);
	}
	
	@PostMapping(value = "/type", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getThemeByType(@Param(value = "type") String type) {
		return service.getThemeByType(type);
	}
	
	@PostMapping(value = "/modules", produces = MediaType.APPLICATION_JSON_VALUE ,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getModulesByTheme(@Param(value = "id") int id) {
		return service.getModulesByTheme(id);
	}
	
	@PostMapping(value = "/affecterMAT", produces = MediaType.APPLICATION_JSON_VALUE ,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject affecterMAT(@Param(value = "idTheme") int idTheme , @Param(value= "idModule") int idModule) {
		return service.affecterMAT(idTheme, idModule);
	}

}
