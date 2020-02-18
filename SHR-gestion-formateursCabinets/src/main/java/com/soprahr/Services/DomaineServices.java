package com.soprahr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.DomaineRepository;
import com.soprahr.models.Domaine;
import net.minidev.json.JSONObject;

@Service
public class DomaineServices {

	@Autowired
	public DomaineRepository repository;
	
	
	/*********************************** AJOUTER UN DOMAINE ***************************************/
	public JSONObject addDomaine(Domaine domaine) {
		JSONObject jo = new JSONObject();
		jo.put("domaine",repository.save(domaine));
		return jo;
	}
	
	/*********************************** LISTE DOMAINES ***************************************/
	public JSONObject getAllDomaines() {
		JSONObject jo = new JSONObject();
		if ( repository.findAll().size() != 0 ) {
			jo.put("domaines" , repository.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des domaines est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UN Domaine ***************************************/
	public JSONObject deleteDomaine(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			repository.delete(repository.findById(id).get());
			jo.put("Success", "Domaine supprimé");
			return jo;
		}else {
			jo.put("Error" , "Domaine n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UN DOMAINE PAR ID ***************************************/
	public JSONObject getDomaineById(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			jo.put("Domaine", repository.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Domaine n'existe pas !");
			return jo;
		}
	}

}
