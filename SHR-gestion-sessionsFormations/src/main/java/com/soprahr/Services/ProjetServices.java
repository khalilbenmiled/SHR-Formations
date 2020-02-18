package com.soprahr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soprahr.Repository.ProjetRepository;
import com.soprahr.models.Projet;
import net.minidev.json.JSONObject;

@Service
public class ProjetServices {

	@Autowired
	public ProjetRepository repositoryP;
	
	
	
	/*********************************** AJOUTER UN PROJET ***************************************/
	public JSONObject addProjet(Projet projet) {
		JSONObject jo = new JSONObject();
		jo.put("Projet",repositoryP.save(projet));
		return jo;
	}
	
	/*********************************** LISTE PROJETS ***************************************/
	public JSONObject getAllProjets() {
		JSONObject jo = new JSONObject();
		if ( repositoryP.findAll().size() != 0 ) {
			jo.put("Projets" , repositoryP.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des projets est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UN PROJET ***************************************/
	public JSONObject deleteProjet(int id) {
		JSONObject jo = new JSONObject();
		if(repositoryP.findById(id).isPresent()) {
			repositoryP.delete(repositoryP.findById(id).get());
			jo.put("Success", "Projet supprimé");
			return jo;
		}else {
			jo.put("Error" , "Projet n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UN PROJET PAR ID ***************************************/
	public JSONObject getProjetById(int id) {
		JSONObject jo = new JSONObject();
		if(repositoryP.findById(id).isPresent()) {
			jo.put("Projet", repositoryP.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Projet n'existe pas !");
			return jo;
		}
	}
	
}
