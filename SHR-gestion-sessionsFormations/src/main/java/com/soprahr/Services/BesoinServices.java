package com.soprahr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soprahr.Repository.BesoinsRepository;
import com.soprahr.models.Besoin;
import net.minidev.json.JSONObject;

@Service
public class BesoinServices {

	@Autowired
	public BesoinsRepository repositoryB;
	
	
	
	
	/*********************************** AJOUTER UN BESOIN ***************************************/
	public JSONObject addBesoin(Besoin besoin) {
		JSONObject jo = new JSONObject();
		jo.put("Besoin",repositoryB.save(besoin));
		return jo;
	}
	
	/*********************************** LISTE BESOINS ***************************************/
	public JSONObject getAllBesoins() {
		JSONObject jo = new JSONObject();
		if ( repositoryB.findAll().size() != 0 ) {
			jo.put("Besoins" , repositoryB.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des besoins est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UN BESOIN ***************************************/
	public JSONObject deleteBesoin(int id) {
		JSONObject jo = new JSONObject();
		if(repositoryB.findById(id).isPresent()) {
			repositoryB.delete(repositoryB.findById(id).get());
			jo.put("Success", "Besoin supprimé");
			return jo;
		}else {
			jo.put("Error" , "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UN BESOIN PAR ID ***************************************/
	public JSONObject getBesoinById(int id) {
		JSONObject jo = new JSONObject();
		if(repositoryB.findById(id).isPresent()) {
			jo.put("Besoin", repositoryB.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Besoin n'existe pas !");
			return jo;
		}
	}
	

	
	
	
	
	
	
	
	
	
	
	
}
