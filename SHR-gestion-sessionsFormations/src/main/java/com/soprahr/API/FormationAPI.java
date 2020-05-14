package com.soprahr.API;


import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprahr.Services.FormationServices;
import com.soprahr.Services.ReportingFormationsService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value ="/formations")
public class FormationAPI {

	@Autowired
	public FormationServices service;
	
	@Autowired
	public ReportingFormationsService reporting;
	

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllFormations() {
		return service.getAllFormations();
	}
	
	@PostMapping(value="/delete" , produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject deleteFormation(@Param(value = "id") int id) {
		return service.deleteFormation(id);
	}

	@PostMapping(value = "/byId" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getFormationById(@Param(value = "id") int id) {
		return service.getFormationById(id);
	}
	
	@PostMapping(value = "/" , produces = MediaType.APPLICATION_JSON_VALUE)
	@SuppressWarnings("rawtypes")
	public JSONObject ajouterFormation(
			@RequestBody JSONObject formation
			) {
		String nomTheme = formation.getAsString("nomTheme");
		String typeTheme = formation.getAsString("typeTheme");
		String dateDebut = formation.getAsString("dateDebut");
		String dateFin = formation.getAsString("dateFin");
		String maxParticipants =  formation.getAsString("maxParticipants");
		String duree = formation.getAsString("duree");
		String idSession = formation.getAsString("idSession");
		String quarter = formation.getAsString("quarter");	
		String idCF = formation.getAsString("idCF");
		
		ArrayList arrayModules = (ArrayList) formation.get("listModules");
		ArrayList modules = (ArrayList) arrayModules.get(0);
		
		ArrayList arrayParticipants = (ArrayList) formation.get("listParticipants");

		return service.ajouterFormation(nomTheme,typeTheme,dateDebut, dateFin, Integer.parseInt(maxParticipants), Integer.parseInt(duree) , Integer.parseInt(idSession),Integer.parseInt(quarter) , modules , arrayParticipants,Integer.parseInt(idCF));
	}
	
	@PostMapping(value = "/participants" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getListParticipants(@Param(value = "id") int id) {
		return service.gettListParticipants(id);
	}
	
	@PostMapping(value = "/collaborateurWithoutParticipant" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getCollaborateurWithoutParticipants(@Param(value = "id") int id) {
		return service.getCollaborateurWithoutParticipants(id);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/setListParticipantFormation" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject setListParticipantFormation(@RequestBody JSONObject objet) {
		String idFormation = objet.getAsString("id");
		ArrayList arrayParticipants = (ArrayList) objet.get("participants");

		return service.setListParticipantFormation(Integer.parseInt(idFormation), arrayParticipants);
	}
	
	
	@PostMapping(value = "/getFormationsWithouThistId" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getFormationsWithouThistId(@Param(value = "id") int id) {
		return service.getFormationsWithouThistId(id);
	}
	
	@PostMapping(value = "/byCollaborateur" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getFormationByParticipant(@Param(value = "id") int id) {
		return service.getFormationByParticipant(id);
	}
	
	@PostMapping(value = "/rate" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject rateFormation(@Param(value = "idFormation") int idFormation , @Param(value = "star") int star) {
		return service.rateFormation(idFormation , star);
	}
	
	@PostMapping(value = "/reporting/rating" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getFormationRating(@Param(value = "id") int id) {
		return reporting.getFormationRating(id);
	}
	
	
	
	
	

	
}
