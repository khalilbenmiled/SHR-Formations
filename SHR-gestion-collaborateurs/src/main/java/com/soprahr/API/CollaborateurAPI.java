package com.soprahr.API;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.soprahr.Services.CollaborateurServices;
import net.minidev.json.JSONObject;


@RestController
@RequestMapping(value = "/collaborateurs")
public class CollaborateurAPI {
	
	
	@Autowired
	public CollaborateurServices service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getCollaborateurs() {
		return service.getAllCollaborateurs();
	}
	
	@DeleteMapping(value="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteCollaborateur(@PathVariable(value="id") int id) {
		return service.deleteCollaborateur(id);
	}
		
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject addCollaborateur(@Param(value = "idCollaborateur") int idCollaborateur , @Param(value = "idTeamLead") int idTeamLead) {
		return service.addCollaborateur(idCollaborateur,idTeamLead);
	}
	
	@PostMapping(value = "/byID", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getCollaborateurById(@Param(value = "id") int id) {
		return service.getCollaborateurById(id);
	}

	@PostMapping(value = "/ByTL", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject collaborateurByTL(@Param(value = "id") int id) {
		return service.getCollaborateurByTL(id);
	}
	
	@PostMapping(value = "/deleteTeamLead", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject deleteTeamLead(@Param(value = "id") int id) {
		return service.deleteTeamLead(id);
	}
	
	@PostMapping(value = "/getTLCollaborateur", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getTLCollaborateur(@Param(value = "id") int id) {
		return service.getTLCollaborateur(id);
	}
	
	@PostMapping(value = "/setCollaborateur", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject setCollaborateur(@Param(value = "idC") int idC , @Param(value = "idTL") int idTL) {
		return service.setCollaborateur(idC , idTL);
	}
	
	@PostMapping(value = "/parTL", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getListCollaborateurByTL(@Param(value = "id") int id ) {
		return service.getListCollaborateurByTL(id);
	}
	
	
	
	
	
	
	
}
