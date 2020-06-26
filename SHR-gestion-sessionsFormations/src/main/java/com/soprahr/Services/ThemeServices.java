package com.soprahr.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.ModuleRepository;
import com.soprahr.Repository.ThemeRepository;
import com.soprahr.models.Module;
import com.soprahr.models.Theme;

import net.minidev.json.JSONObject;

@Service
public class ThemeServices {

	@Autowired
	public ThemeRepository repository;
	@Autowired
	public ModuleRepository repositoryModule;

	
	/*********************************** AJOUTER UN THEME ***************************************/
	public JSONObject addTheme(Theme theme) {
		JSONObject jo = new JSONObject();
		jo.put("Theme",repository.save(theme));
		return jo;
	}
	
	/*********************************** MODIFIER UN THEME ***************************************/
	public JSONObject modifierTheme (Theme theme) {
		JSONObject jo = new JSONObject();
		if(repository.findById(theme.getId()).isPresent()) {
			Theme t = repository.findById(theme.getId()).get();
			t.setType(theme.getType());
			t.setNom(theme.getNom());
			
			
			jo.put("Theme" , repository.save(t));
			return jo;
		}else {
			jo.put("Error" , "Theme n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** LISTE THEMES ***************************************/
	public JSONObject getAllThemes() {
		JSONObject jo = new JSONObject();
		if ( repository.findAllThemes().size() != 0 ) {
			jo.put("Theme" , repository.findAllThemes());
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
			Theme theme = repository.findById(id).get();
			theme.setDeleted(true);
			repository.save(theme);
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
	
	/*********************************** RECHERCHER UN THEME PAR TYPE ***************************************/
	public JSONObject getThemeByType(String type) {
		
		JSONObject jo = new JSONObject();
		List<Theme> theme = repository.getThemeByType(type);
		if(theme.size() == 0) {
			jo.put("Error", "Theme n'existe pas !");
			return jo;
		}else {
			jo.put("Theme", theme);
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UN MODULES PAR THEME ***************************************/
	public JSONObject getModulesByTheme(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			Theme theme = repository.findById(id).get();
			jo.put("Modules", theme.getListModules());
			jo.put("Theme" , theme.getNom());
			return jo;
		}else {
			jo.put("Error", "Theme n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** AFFECTER UN MODULE A UN THEME ***************************************/
	public JSONObject affecterMAT(int idTheme , int idModule) {
		JSONObject jo = new JSONObject();
		if(repository.findById(idTheme).isPresent()) {
			Theme theme = repository.findById(idTheme).get();
			if(repositoryModule.findById(idModule).isPresent()) {
				Module module = repositoryModule.findById(idModule).get();
				List<Module> listModules = theme.getListModules();
				listModules.add(module);
				theme.setListModules(listModules);
				
				jo.put("Success", repository.save(theme));
				return jo;
			}else {
				jo.put("Error", "Module n'existe pas !");
				return jo;
			}
		}else {
			jo.put("Error", "Theme n'existe pas !");
			return jo;
		}
	}
	
	
}








