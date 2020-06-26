package com.soprahr.API;


import java.util.ArrayList;
import java.util.LinkedHashMap;

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
	
	@GetMapping(value = "/allFormations" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllFormationsWithDeleted() {
		return service.getAllFormationsWithDeleted();
	}
	
	@PostMapping(value="/delete" , produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject deleteFormation(@Param(value = "id") int id) {
		return service.deleteFormation(id);
	}
	
	@PostMapping(value="/modifier" , produces = MediaType.APPLICATION_JSON_VALUE )
	public JSONObject modifierFormation(@RequestBody JSONObject formation) {
		
		return service.modifierFormation(formation);
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
	
	@PostMapping(value = "/convoquer" , produces = MediaType.APPLICATION_JSON_VALUE)
	@SuppressWarnings("rawtypes")
	public JSONObject convoquerParticipants(@RequestBody JSONObject input) {
		ArrayList arrayParticipant = (ArrayList) input.get("listParticipants");
		LinkedHashMap formation = (LinkedHashMap) input.get("formation");
		
		return service.convoquerParticipants(arrayParticipant , formation);
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
	
	@PostMapping(value = "/reporting/etat" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getFormationByEtat(
			@Param(value="theme")String theme ,
			@Param(value="dateDebut")String dateDebut ,
			@Param(value="dateFin")String dateFin , 
			@Param(value="type")String type
			) {
		return reporting.getFormationByEtat(theme,dateDebut, dateFin,type);
	}
	
	@PostMapping(value = "/reporting/etatByCollaborateur" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getFormationByEtatAndCollaborateur(
			@Param(value="theme")String theme ,
			@Param(value="dateDebut")String dateDebut ,
			@Param(value="dateFin")String dateFin , 
			@Param(value="type")String type,
			@Param(value="id")int id
			) {
		return reporting.getFormationByEtatAndCollaborateur(theme,dateDebut, dateFin,type,id);
	}
	
	@PostMapping(value = "/reporting/etatByTL" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getFormationByEtatByTeamLead(
			@Param(value="theme")String theme ,
			@Param(value="dateDebut")String dateDebut ,
			@Param(value="dateFin")String dateFin , 
			@Param(value="type")String type,
			@Param(value="id")int id
			) {
		return reporting.getFormationByEtatByTeamLead(theme,dateDebut, dateFin,type,id);
	}
	
	@PostMapping(value = "/reporting/etatByManager" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getFormationByEtatByManager(
			@Param(value="theme")String theme ,
			@Param(value="dateDebut")String dateDebut ,
			@Param(value="dateFin")String dateFin , 
			@Param(value="type")String type,
			@Param(value="id")int id
			) {
		return reporting.getFormationByEtatByManager(theme,dateDebut, dateFin,type,id);
	}
	
	@PostMapping(value = "/byUser" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getFormationsByUser(@Param(value = "id") int id) {
		return service.getFormationsByUser(id);
	}
	
	@PostMapping(value = "/setFormateurCabinet" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject setFormateur(@Param(value = "idFormation") int idFormation , @Param(value = "formateurCabinet") int formateurCabinet) {
		return service.setFormateur(idFormation,formateurCabinet);
	}
	
	
	
	
	
	

	
}
