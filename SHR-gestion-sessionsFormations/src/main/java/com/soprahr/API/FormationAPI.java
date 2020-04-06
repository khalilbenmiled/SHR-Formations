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

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value ="/formations")
public class FormationAPI {

	@Autowired
	public FormationServices service;
	
//	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//	public JSONObject addFormation(@RequestBody Formation formation) {
//		return service.addFormation(formation);
//	}

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
	
	@PostMapping(value = "/" , produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject ajouterFormation(
			@Param(value = "nomTheme") String nomTheme , @Param(value = "typeTheme") String typeTheme,
			@Param(value="dateDebut") String dateDebut , @Param(value="dateFin") String dateFin, @Param(value = "maxParticipants") int maxParticipants , @Param(value = "duree") float duree,
			@Param(value = "idSession") int idSession
			) {
		return service.ajouterFormation(nomTheme,typeTheme,dateDebut, dateFin, maxParticipants, duree , idSession);
	}
	
	@PostMapping(value = "/test" , produces = MediaType.APPLICATION_JSON_VALUE )
	public JSONObject test (@RequestBody JSONObject listModules) {
		System.out.println(listModules);
		return null;
	}
	
}
