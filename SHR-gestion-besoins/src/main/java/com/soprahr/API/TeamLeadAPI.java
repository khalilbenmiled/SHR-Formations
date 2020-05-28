package com.soprahr.API;

import java.util.ArrayList;

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

import com.soprahr.Services.TeamLeadService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/teamlead")
public class TeamLeadAPI {

	@Autowired
	public TeamLeadService service;
	
	
	@PostMapping(value = "/byID", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getTeamLeadById(@Param(value = "id") int id ) {
		return service.getTeamLeadById(id);
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject addTeamLead(@Param(value = "idTL") int idTL, @Param(value = "idMG") int idMG  ) {
		return service.addTeamLead(idTL , idMG);
	}
	
	@DeleteMapping(value="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteTeamLead(@PathVariable(value = "id") int id) {
		return service.deleteTeamLead(id);
	}
	
	@PostMapping(value = "/affecterTLMG" , produces = MediaType.APPLICATION_JSON_VALUE)
	@SuppressWarnings("rawtypes")
	public JSONObject affecterTLMG(@RequestBody JSONObject input) {
		ArrayList listTeamLead = (ArrayList) input.get("listTeamLead");
		String idManager = input.getAsString("idManager");
		return service.affecterTLMG(listTeamLead, Integer.parseInt(idManager));
	}
	
	@DeleteMapping(value="/manager/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteManagerFromTL(@PathVariable(value = "id") int id) {
		return service.deleteManagerFromTL(id);
	}
	
	@PostMapping(value = "/getManagerTL", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getManagerTL(@Param(value = "id") int id  ) {
		return service.getManagerTL(id);
	}
	
	@PostMapping(value = "/setManager", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject setManager(@Param(value = "idTL") int idTL , @Param(value = "idMG") int idMG) {
		return service.setManager(idTL,idMG);
	}
	
	@GetMapping(value = "/getFreeTL", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getFreeTL( ) {
		return service.getFreeTL();
	}
	
	@PostMapping(value = "/byManager", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getListTeamLeadByManager(@Param(value = "id") int id) {
		return service.getListTeamLeadByManager(id);
	}
	
	
	
	
}
