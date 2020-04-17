package com.soprahr.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.FormationRepository;
import com.soprahr.Repository.SessionRepository;
import com.soprahr.models.Formation;
import com.soprahr.models.Session;

import net.minidev.json.JSONObject;

@Service
public class SessionServices {

	@Autowired
	public SessionRepository repository;
	@Autowired
	public FormationRepository repositoryF;
	

	/*********************************** AJOUTER UNE SESSION ***************************************/
	public JSONObject addSession(Session session) {
		JSONObject jo = new JSONObject();
		jo.put("Session",repository.save(session));
		return jo;
	}
	
	/*********************************** LISTE SESSIONS ***************************************/
	public JSONObject getAllSession() {
		JSONObject jo = new JSONObject();
		if ( repository.findAll().size() != 0 ) {
			jo.put("Sessions" , repository.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des sessions est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UNE SESSION ***************************************/
	public JSONObject deleteSession(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			repository.delete(repository.findById(id).get());
			jo.put("Success", "Session supprimé");
			return jo;
		}else {
			jo.put("Error" , "Session n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UNE SESSION PAR ID ***************************************/
	public JSONObject getSessionById(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			jo.put("Session", repository.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Session n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER SESSION PAR FORMATION ***************************************/
	public JSONObject getSessionByFormation(int idFormation) {
		JSONObject jo = new JSONObject();
		if(repositoryF.findById(idFormation).isPresent()) {
			if(repository.findAll().size() != 0) {
				List<Session> listSessions = repository.findAll();
				for(Session session : listSessions) {
					List<Formation> listFormations = session.getListFormations();
					
					for (Formation formation : listFormations) {
						System.out.println(formation);
						if(formation.getId() == idFormation) {
							
							jo.put("Session" , session);
							return jo;
						}
					}
				}
				
				jo.put("Error", "Formation n'existe pas !");
				return jo;
			}else {
				jo.put("Error" , "List session est vide");
				return jo;
			}
		}else {
			jo.put("Error", "Formation n'existe pas");
			return jo;
		}
	}

	/*********************************** RECHERCHER SESSIONS PAR QUARTER ***************************************/
	public JSONObject getSessionByQuarter(int quarter) {
		JSONObject jo = new JSONObject();
		if(repository.getSessionByQuarter(quarter) != null) {
			List<Session> sessions = repository.getSessionByQuarter(quarter);
			jo.put("Sessions" , sessions);
			return jo;
		}else {
			jo.put("Error", "Session n'existe pas !");
			return jo;
		}
	}

	
	
	
	
	
	
	
	
	
	
	
}
