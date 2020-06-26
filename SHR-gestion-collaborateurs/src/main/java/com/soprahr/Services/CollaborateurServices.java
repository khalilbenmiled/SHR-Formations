package com.soprahr.Services;

import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.soprahr.Repository.CollaborateurRepository;
import com.soprahr.Utils.PROXY;
import com.soprahr.models.Collaborateur;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Service
public class CollaborateurServices {

	@Autowired
	public CollaborateurRepository repository;

	/*********************************** AJOUTER UN COLLABORATEUR ***************************************/
	public JSONObject addCollaborateur(int idC,int idTL) {
		JSONObject jo = new JSONObject();
		Collaborateur collaborateur = new Collaborateur();
		collaborateur.setIdCollaborateur(idC);
		collaborateur.setIdTeamLeader(idTL);
		jo.put("Collaborateur", repository.save(collaborateur));
		return jo;
	}
	
	/*********************************** AFFICHER UN COLLABORATEUR PAR ID_COLLABORATEUR ***************************************/
	public JSONObject getCollaborateurById(int id) {
		JSONObject jo = new JSONObject();
		if(repository.getCollaborateurByIdCollaborateur(id) != null) {
			jo.put("Collaborateur" ,repository.getCollaborateurByIdCollaborateur(id));
			return jo;
		}else {
			jo.put("Error" ,"Collaborateur n'existe pas !");
			return jo;
		}

	}

	/*********************************** LISTE COLLABORATEURS ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject getAllCollaborateurs() {
		JSONObject jo = new JSONObject();
		final String uri = "http://127.0.0.1:8181/users";
		RestTemplate restTemplate = new RestTemplate();
		JSONObject result = restTemplate.getForObject(uri, JSONObject.class);
		jo.put("Users", result);
		ArrayList array = (ArrayList) result.get("Users");
		LinkedHashMap o = (LinkedHashMap) array.get(0);
		System.out.println(o);
		return jo;
	}
	
	/*********************************** SUPPRIMER UN COLLABORATEUR ***************************************/
	public JSONObject deleteCollaborateur(int id) {
		JSONObject jo = new JSONObject();
		if(repository.getCollaborateurByIdCollaborateur(id) != null) {
			Collaborateur collaborateur = repository.getCollaborateurByIdCollaborateur(id);
			repository.delete(collaborateur);
			jo.put("Success" , "Collaborateur supprimé !");
			return jo;
		}else {
			jo.put("Error", "Collaborateur n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** COLLABORATEUR PAR TEAM LEAD ***************************************/
	public JSONObject getCollaborateurByTL(int idTL) {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		if(repository.getCollaborateurByTL(idTL).size() != 0 ) {
			
			final String uri = PROXY.Utilisateurs+"/users/byId";
			
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			List<Collaborateur> listCollaborateurs =  repository.getCollaborateurByTL(idTL);
			JSONObject obj2 = new JSONObject();
			for (Collaborateur c : listCollaborateurs) {
				
				MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
				map.add("id", c.getIdCollaborateur());
				HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
				ResponseEntity<JSONObject> response = restTemplate.postForEntity( uri, request , JSONObject.class );
				JSONObject obj = new JSONObject();
				obj.put("Collaborateur", c);
				obj.put("Informations" , response.getBody().get("User"));
				JSONObject user = new JSONObject();
				user.put("User", obj);
				ja.appendElement(user);
				
				obj2.appendField("Users" , ja);
				
			}
			return obj2;
		}else {
			jo.put("Error" , "Ce TEAM LEAD n'a pas de collaborateurs");
			return jo;
		}
	}
	
	/*********************************** GET COLLABORATEUR PAR TEAM LEAD AND SET TEAM LEAD NULL***************************************/
	public JSONObject deleteTeamLead(int id) {
		JSONObject jo = new JSONObject();
		if(repository.getCollaborateurByTL(id).size() != 0) {
			List<Collaborateur> listCollaborateurs = repository.getCollaborateurByTL(id);
			for(Collaborateur collaborateur : listCollaborateurs) {
				collaborateur.setIdTeamLeader(0);
				repository.save(collaborateur);
			}
			jo.put("Collaborateur" , "Collaborateurs a jour");
			return jo;
		}else {
			jo.put("Error", "Collaborateur n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** GET TEAMLEAD COLLABORATEUR ***************************************/
	public JSONObject getTLCollaborateur(int idCollaborateur) {
		JSONObject jo = new JSONObject();
		if(repository.getCollaborateurByIdCollaborateur(idCollaborateur) != null) {
			Collaborateur collaborateur = repository.getCollaborateurByIdCollaborateur(idCollaborateur);
			jo.put("idTL", collaborateur.getIdTeamLeader());
			return jo;
		}else {
			jo.put("Collaborateur" , "Collaborateurs n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** SET TEAMLEAD COLLABORATEUR ***************************************/
	public JSONObject setCollaborateur(int idC , int idTL) {
		JSONObject jo = new JSONObject();
		if(repository.getCollaborateurByIdCollaborateur(idC) != null) {
			Collaborateur collaborateur = repository.getCollaborateurByIdCollaborateur(idC);
			collaborateur.setIdTeamLeader(idTL);
			jo.put("Collaborateur", repository.save(collaborateur));
			return jo;
		}else {
			jo.put("Collaborateur" , "Collaborateurs n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** LIST COLLABORATEUR PAR ID TL ***************************************/
	public JSONObject getListCollaborateurByTL(int id) {
		JSONObject jo = new JSONObject();
		if (repository.getCollaborateurByTL(id).size() != 0 ) {
			jo.put("Collaborateurs", repository.getCollaborateurByTL(id));
			return jo;
		}else {
			jo.put("Error", "La liste est vide ! ");
			return jo;
		}
	}
	
	
	
	
	
	
	

}
