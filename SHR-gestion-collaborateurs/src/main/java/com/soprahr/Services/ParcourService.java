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
import com.soprahr.Repository.FormationRepository;
import com.soprahr.Repository.ParcourRepository;
import com.soprahr.Utils.PROXY;
import com.soprahr.models.Collaborateur;
import com.soprahr.models.Formation;
import com.soprahr.models.Parcour;

import net.minidev.json.JSONObject;

@Service
public class ParcourService {

	@Autowired
	public ParcourRepository repository;
	@Autowired
	public CollaborateurRepository repositoryC;
	@Autowired
	public FormationRepository repositoryF;
	

	/*********************************** AJOUTER UN PARCOUR ***************************************/
	public JSONObject addParcour(int idCollaborateur , int idFormation ) {
		JSONObject jo = new JSONObject();
	
		
		List<Formation> listFormations = new ArrayList<Formation>(); 
		if(repositoryC.getCollaborateurByIdCollaborateur(idCollaborateur) != null) {
			Formation formation = new Formation();
			formation.setIdFormation(idFormation);	
			listFormations.add(repositoryF.save(formation));
			Collaborateur collaborateur = repositoryC.getCollaborateurByIdCollaborateur(idCollaborateur);
			if(repository.getParcourByCollaborateur(collaborateur.getId()) != null) {
				Parcour parcour = repository.getParcourByCollaborateur(collaborateur.getId());
				listFormations.addAll(parcour.getListFormations());
				parcour.setListFormations(listFormations);
				jo.put("Parcour", repository.save(parcour));
				return jo;
				
			}else {
				Parcour parcour = new Parcour();
				parcour.setCollaborateur(repositoryC.getCollaborateurByIdCollaborateur(idCollaborateur));
				parcour.setListFormations(listFormations);
				jo.put("Parcour", repository.save(parcour));
				return jo;
			}
		}else {
			jo.put("Error", "Collaborateur n'existe pas !");
			return jo;
		}
	}
	
	
	/*********************************** GET MON PARCOUR ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject getParcour(int idCollaborateur) {
		JSONObject jo = new JSONObject();
		Collaborateur collaborateur = repositoryC.getCollaborateurByIdCollaborateur(idCollaborateur);
		if(collaborateur == null) {
			jo.put("Error" , "Erreur utilisateur");
			return jo;
		}
		if(repository.getParcourByCollaborateur(collaborateur.getId()) != null) {
			
			Parcour parcour = repository.getParcourByCollaborateur(collaborateur.getId());
			List<Object> listFormations = new ArrayList<Object>();
			
			for(Formation formation : parcour.getListFormations()) {
				int idFormation = formation.getIdFormation();
				ResponseEntity<JSONObject> formationsResponse = getFormationByID(PROXY.SessionsFormations+"/formations/byId",idFormation);
				if(formationsResponse.getBody().containsKey("Error")) {
					jo.put("Error" , formationsResponse.getBody().get("Error"));
					return jo;
				}else {
					LinkedHashMap f = (LinkedHashMap) formationsResponse.getBody().get("Formation");
					listFormations.add(f);
				}
			}
			JSONObject joo = new JSONObject();
			joo.put("Parcour", parcour);
			joo.put("Formations", listFormations);
			jo.put("Results" , joo);
			return jo;
		}else {
			jo.put("Error", "Vous n'avez pas de parcour !");
			return jo;
		}
	}
	
	
	/*********************************** GET ALL PARCOUR ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject getAllParcours() {
		JSONObject jo = new JSONObject();
		JSONObject joo = new JSONObject();
		if(repository.findAll().size() != 0) {
			List<JSONObject> listJSON = new ArrayList<JSONObject>();
			List<Parcour> listParcours = repository.findAll();
			
			for(Parcour parcour : listParcours) {
				
				List<Object> listFormations = new ArrayList<Object>();
				
				for(Formation formation : parcour.getListFormations()) {
					int idFormation = formation.getIdFormation();
					ResponseEntity<JSONObject> formationsResponse = getFormationByID(PROXY.SessionsFormations+"/formations/byId",idFormation);
					if(formationsResponse.getBody().containsKey("Error")) {
						jo.put("Error" , formationsResponse.getBody().get("Error"));
						return jo;
					}else {
						LinkedHashMap f = (LinkedHashMap) formationsResponse.getBody().get("Formation");
						listFormations.add(f);
					}
				}
				jo.put("Parcour", parcour);
				jo.put("Formations", listFormations);
				listJSON.add(jo);
			}
			joo.put("Results", listJSON);
			return joo;
		}else {
			jo.put("Error", "Vous n'avez pas de parcour !");
			return jo;
		}
	}
	
	
	/*********************************** API FORMATION BY ID ***************************************/
	public ResponseEntity<JSONObject> getFormationByID(String uri , int id) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
		map.add("id", id);
		HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
		ResponseEntity<JSONObject> response = restTemplate.postForEntity( uri, request , JSONObject.class );
		return response;
	}
	
	
	/*********************************** API FORMATION BY ID ***************************************/
	public ResponseEntity<JSONObject> getScoreFormation(String uri , int id) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
		map.add("id", id);
		HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
		ResponseEntity<JSONObject> response = restTemplate.postForEntity( uri, request , JSONObject.class );
		return response;
	}
	
	
	
	
	
	
	
	
	
	
}
