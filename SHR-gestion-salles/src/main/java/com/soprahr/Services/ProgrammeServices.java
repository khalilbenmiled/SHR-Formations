package com.soprahr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soprahr.Repository.ProgrammeRepository;
import com.soprahr.models.Programme;
import net.minidev.json.JSONObject;

@Service
public class ProgrammeServices {


	@Autowired
	public ProgrammeRepository repository;

	
	/*********************************** AJOUTER UN PROGRAMME ***************************************/
	public JSONObject addProgramme(Programme programme) {
		JSONObject jo = new JSONObject();
		jo.put("Programme",repository.save(programme));
		return jo;
	}
	
	/*********************************** LISTE PROGRAMMES ***************************************/
	public JSONObject getAllProgrammes() {
		JSONObject jo = new JSONObject();
		if ( repository.findAll().size() != 0 ) {
			jo.put("Programmes" , repository.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des programmes est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UN PROGRAMME ***************************************/
	public JSONObject deleteProgramme(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			repository.delete(repository.findById(id).get());
			jo.put("Success", "Programme supprimé");
			return jo;
		}else {
			jo.put("Error" , "Programme n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UN PROGRAMME PAR ID ***************************************/
	public JSONObject getProgrammeById(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			jo.put("Programme", repository.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Programme n'existe pas !");
			return jo;
		}
	}
	
}
