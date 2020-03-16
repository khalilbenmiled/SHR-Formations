package com.soprahr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.SessionRepository;
import com.soprahr.models.Session;

import net.minidev.json.JSONObject;

@Service
public class SessionServices {

	@Autowired
	public SessionRepository repository;
	

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
}
