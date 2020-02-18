package com.soprahr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.ThemeRepository;
import com.soprahr.models.Theme;

import net.minidev.json.JSONObject;

@Service
public class ThemeServices {

	@Autowired
	public ThemeRepository repository;
	
	/*********************************** AJOUTER UN THEME ***************************************/
	public JSONObject addTheme(Theme theme) {
		JSONObject jo = new JSONObject();
		jo.put("Theme",repository.save(theme));
		return jo;
	}
	
	/*********************************** LISTE THEMES ***************************************/
	public JSONObject getAllThemes() {
		JSONObject jo = new JSONObject();
		if ( repository.findAll().size() != 0 ) {
			jo.put("Theme" , repository.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des themes est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UN THEME ***************************************/
	public JSONObject deleteTheme(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			repository.delete(repository.findById(id).get());
			jo.put("Success", "Theme supprimé");
			return jo;
		}else {
			jo.put("Error" , "Theme n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UN THEME PAR ID ***************************************/
	public JSONObject getThemeById(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			jo.put("Theme", repository.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Theme n'existe pas !");
			return jo;
		}
	}
}
