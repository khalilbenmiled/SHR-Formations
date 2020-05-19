package com.soprahr.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soprahr.Repository.ProjetRepository;
import com.soprahr.Repository.TeamLeadRepository;
import com.soprahr.model.Projet;
import com.soprahr.model.TeamLead;

import net.minidev.json.JSONObject;

@Service
public class ProjetServices {

	@Autowired
	public ProjetRepository repository;
	@Autowired
	public TeamLeadRepository repositoryTL;
	
	/*********************************** AJOUTER UN PROJET ***************************************/
	public JSONObject addProjet(Projet projet) {
		JSONObject jo = new JSONObject();
		jo.put("Projet",repository.save(projet));
		return jo;
	}
	
	
	/*********************************** LISTE PROJETS ***************************************/
	public JSONObject getAllProjets() {
		JSONObject jo = new JSONObject();
		if ( repository.findAll().size() != 0 ) {
			jo.put("Projets" , repository.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des projets est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UN PROJET ***************************************/
	public JSONObject deleteProjet(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			repository.delete(repository.findById(id).get());
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
		if(repository.findById(id).isPresent()) {
			jo.put("Projet", repository.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Projet n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** PROJET PAR TEAM LEAD ***************************************/
	public JSONObject getProjetByTL (int id) {
		JSONObject jo = new JSONObject();
		if (repository.getProjetByTL(id).size() != 0 ) {
			List<Projet> listProjets = new ArrayList<Projet>();
			listProjets.addAll(repository.getProjetByTL(id));
			
			TeamLead teamlead = repositoryTL.getTeamLeadById(id);
			listProjets.addAll(repository.getProjetByMG(teamlead.getIdManager()));
			jo.put("Projets" ,listProjets) ;
			return jo;
		}else {
			jo.put("Error" , "Ce team lead n'a pas de projet");
			return jo;
		}
	}
	
	/*********************************** PROJET PAR MANAGER ***************************************/
	public JSONObject getProjetByMG (int id) {
		JSONObject jo = new JSONObject();
		List<Projet> listProjets = new ArrayList<Projet>();
		List<TeamLead> listTL = repositoryTL.getTeamLeadByManager(id);
		for (TeamLead tl : listTL) {
			if (repository.getProjetByTL(tl.getIdTeamLead()).size() != 0 ) {
				listProjets.addAll(repository.getProjetByTL(tl.getIdTeamLead()));
			}
		}
		if(repository.getProjetByMG(id).size() != 0) {
			listProjets.addAll(repository.getProjetByMG(id));
		}

		jo.put("Projets", listProjets);
		return jo;
	}
	
	
	
	
	
	
	
	
	
}
