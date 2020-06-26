package com.soprahr.Services;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.soprahr.Repository.TeamLeadRepository;
import com.soprahr.Utils.PROXY;
import com.soprahr.model.TeamLead;

import net.minidev.json.JSONObject;

@Service
public class TeamLeadService {

	@Autowired
	public TeamLeadRepository repository;
	
	/*********************************** AJOUTER TEAMEAD ***************************************/
	public JSONObject addTeamLead(int idTL , int idMG) {
		JSONObject jo = new JSONObject();
		TeamLead teamlead = new TeamLead();
		teamlead.setIdTeamLead(idTL);
		teamlead.setIdManager(idMG);
		jo.put("TeamLead", repository.save(teamlead));
		return jo;
	}
	
	/*********************************** AFFICHER TEAMLEAD PAR ID ***************************************/
	public JSONObject getTeamLeadById(int id) {
		JSONObject jo = new JSONObject();
		if(repository.getTeamLeadById(id) != null) {
			jo.put("TeamLead", repository.getTeamLeadById(id));
			return jo;
		}else {
			jo.put("Error", "TeamLead n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** AFFECTER TEAMLEAD A UN MANAGER ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject affecterTLMG(ArrayList listTeamlead , int idManager) {
		 JSONObject jo = new JSONObject();
		 for(Object obj : listTeamlead) {
			 LinkedHashMap teamlead = (LinkedHashMap) obj;
			 int id = (int) teamlead.get("id");
			 if(repository.getTeamLeadById(id) != null) {
				 TeamLead tl = repository.getTeamLeadById(id);
				 tl.setIdManager(idManager);
				 repository.save(tl);
			 }
		 }
		 jo.put("Success", "Success");
		 return jo;
	}
	
	/*********************************** SUPPRIMER LES MANAGER DE CES TEAMLEAD ***************************************/
	public JSONObject deleteManagerFromTL(int idManager) {
		JSONObject jo = new JSONObject();
		if(repository.getTeamLeadByManager(idManager).size() != 0) {
			for(TeamLead teamlead : repository.getTeamLeadByManager(idManager)) {
				teamlead.setIdManager(0);
				repository.save(teamlead);
			}
			jo.put("Success", "UPDATED");
			return jo;
		}else {
			jo.put("Error" , "List est vide !");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER TEAMLEAD PAR ID ***************************************/
	public JSONObject deleteTeamLead (int id) {
		JSONObject jo = new JSONObject();
		if(repository.getTeamLeadById(id) != null) {
			TeamLead teamlead = repository.getTeamLeadById(id);
			repository.delete(teamlead);
			
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
			map.add("id", id);
			HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
			restTemplate.postForEntity(PROXY.Collaborateurs+"/collaborateurs/deleteTeamLead", request , JSONObject.class );
			jo.put("Success", "TeamLead supprimé");
			return jo;
		}else {
			jo.put("Error", "Team lead n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** GET MANAGER TEAMLEAD ***************************************/
	public JSONObject getManagerTL (int id) {
		JSONObject jo = new JSONObject();
		if(repository.getTeamLeadById(id) != null) {
			TeamLead teamlead = repository.getTeamLeadById(id);
			jo.put("idMG", teamlead.getIdManager());
			return jo;
		}else {
			jo.put("Error", "Team lead n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** SET MANAGER TEAMLEAD ***************************************/
	public JSONObject setManager(int idTL,int idMG) {
		JSONObject jo = new JSONObject();
		if(repository.getTeamLeadById(idTL) != null) {
			TeamLead teamlead = repository.getTeamLeadById(idTL);
			teamlead.setIdManager(idMG);
			jo.put("TeamLead", repository.save(teamlead));
			return jo;
		}else {
			jo.put("Error", "Team lead n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** GET FREE TEAMLEAD ***************************************/
	public JSONObject getFreeTL() {
		JSONObject jo = new JSONObject();
		if(repository.getFreeTL().size() != 0) {
			jo.put("TeamLeads", repository.getFreeTL()) ;
			return jo;
		}else {
			jo.put("Error", "La liste est vide !");
			return jo;
		}
	}
	
	/*********************************** GET FREE TEAMLEAD ***************************************/
	public JSONObject getListTeamLeadByManager(int id) {
		JSONObject jo = new JSONObject();
		if(repository.getTeamLeadByManager(id).size() != 0) {
			jo.put("TeamLeads", repository.getTeamLeadByManager(id));
			return jo;
		}else {
			jo.put("Error", "La liste est vide ! ");
			return jo;
		}
	}
	
	
	
	
	
	
}
