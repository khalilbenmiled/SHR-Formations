package com.soprahr.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soprahr.RabbitMQ.RabbitMQSender;
import com.soprahr.Repository.FormationRepository;
import com.soprahr.models.Formation;

import net.minidev.json.JSONObject;


@Service
public class FormationServices {

	@Autowired
	public FormationRepository repository;
	@Autowired
	public RabbitMQSender rabbitMQSender;
	
	
	/*********************************** AJOUTER UNE FORMATION ***************************************/
	public JSONObject addFormation(Formation formation) {
		JSONObject jo = new JSONObject();
		Formation f = repository.save(formation);
		jo.put("Formation",f);
		/*------------------ DECLENCHER UN EVENEMENT AUX AUTRES SERICES --------------------------------*/
		JSONObject obj = new JSONObject();
		obj.put("formation", f.getId());
		rabbitMQSender.send(obj);
		/*----------------------------------------------------------------------------------------------*/
		return jo;
	}
	
	/*********************************** LISTE FORMATIONS ***************************************/
	public JSONObject getAllFormations() {
		JSONObject jo = new JSONObject();
		if ( repository.findAll().size() != 0 ) {
			jo.put("Formations" , repository.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des formations est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UNE FORMATION ***************************************/
	public JSONObject deleteFormation(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			repository.delete(repository.findById(id).get());
			jo.put("Success", "Formation supprimé");
			return jo;
		}else {
			jo.put("Error" , "Formation n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UNE FORMATION PAR ID ***************************************/
	public JSONObject getFormationById(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			jo.put("Formation", repository.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Formation n'existe pas !");
			return jo;
		}
	}
}
