package com.soprahr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soprahr.Repository.FormateurRepository;
import com.soprahr.models.Formateur;
import net.minidev.json.JSONObject;

@Service
public class FormateurServices {

	@Autowired
	public FormateurRepository repository;

	
	/*********************************** AJOUTER UN FORMATEUR ***************************************/
	public JSONObject addFormateur(Formateur formateur) {
		JSONObject jo = new JSONObject();
		jo.put("formateur",repository.save(formateur));
		return jo;
	}
	
	/*********************************** LISTE FORMATEURS ***************************************/
	public JSONObject getAllFormateurs() {
		JSONObject jo = new JSONObject();
		if ( repository.findAll().size() != 0 ) {
			jo.put("formateurs" , repository.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des formateurs est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UN FORMATEUR ***************************************/
	public JSONObject deleteFormateurs(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			repository.delete(repository.findById(id).get());
			jo.put("Success", "Formateur supprimé");
			return jo;
		}else {
			jo.put("Error" , "Formateur n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** MODIFIER UN FORMATEUR ***************************************/
	public JSONObject updateFormateur(Formateur formateur) {
		JSONObject jo = new JSONObject();
		if(repository.findById(formateur.getId()).isPresent()) {
			Formateur f = repository.findById(formateur.getId()).get();
			f.setNom(formateur.getNom());
			f.setPrenom(formateur.getPrenom());
			f.setEmail(formateur.getEmail());
			f.setAdresse(formateur.getAdresse());
			f.setTel(formateur.getTel());
			
			jo.put("Formateur", repository.save(f));
			return jo;
		}else {
			jo.put("Error" , "Formateur n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UN FORMATEUR PAR ID ***************************************/
	public JSONObject getFormateurById(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			jo.put("Formateur", repository.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Formateur n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UN FORMATEUR PAR BU ***************************************/
	public JSONObject getFormateurByBU(int bu) {
		JSONObject jo = new JSONObject();
		Formateur formateur = repository.getFormateurByBU(bu);
		if(formateur != null) {
			jo.put("Formateur" , formateur);
			return jo;
		}else {
			jo.put("Error" , "Formateur n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UN FORMATEUR PAR EXPERTISE ***************************************/
	public JSONObject getFormateurByExpertise(int expertise) {
		JSONObject jo = new JSONObject();
		Formateur formateur = repository.getFormateurByExpertise(expertise);
		if(formateur != null) {
			jo.put("Formateur" , formateur);
			return jo;
		}else {
			jo.put("Error" , "Formateur n'existe pas !");
			return jo;
		}
	}
}
















