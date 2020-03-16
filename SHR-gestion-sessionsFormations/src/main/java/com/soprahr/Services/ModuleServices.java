package com.soprahr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.ModuleRepository;
import com.soprahr.models.Module;
import net.minidev.json.JSONObject;

@Service
public class ModuleServices {

	@Autowired
	public ModuleRepository repository;
	
	
	
	/*********************************** AJOUTER UN MODULE ***************************************/
	public JSONObject addModule(Module module) {
		JSONObject jo = new JSONObject();
		jo.put("Module",repository.save(module));
		return jo;
	}
	
	/*********************************** LISTE MODULES ***************************************/
	public JSONObject getAllModules() {
		JSONObject jo = new JSONObject();
		if ( repository.findAll().size() != 0 ) {
			jo.put("Modules" , repository.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des modules est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UN MODULE ***************************************/
	public JSONObject deleteModule(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			repository.delete(repository.findById(id).get());
			jo.put("Success", "Module supprimé");
			return jo;
		}else {
			jo.put("Error" , "Module n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UN MODULE PAR ID ***************************************/
	public JSONObject getModuleById(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			jo.put("Module", repository.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Module n'existe pas !");
			return jo;
		}
	}
	
	

	
	
	
	
	
	
	
	
	
}
