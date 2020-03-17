package com.soprahr.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.soprahr.Repository.BesoinsRepository;
import com.soprahr.Repository.ProjetRepository;
import com.soprahr.Repository.TeamLeadRepository;
import com.soprahr.model.Besoins;
import com.soprahr.model.Module;
import com.soprahr.model.Projet;
import com.soprahr.model.TeamLead;

import net.minidev.json.JSONObject;

@Service
public class BesoinsService {

	@Autowired
	public BesoinsRepository repository;
	@Autowired
	public ProjetRepository repositoryP;
	@Autowired
	public TeamLeadRepository repositoryTL;
	
	/*********************************** AJOUTER UN BESOIN ***************************************/
	public JSONObject addBesoin(Besoins besoin) {
		JSONObject jo = new JSONObject();
		List<Besoins> listBesoins = repository.getBesoinsByUser(besoin.getIdUser());
		if(listBesoins.size() == 0) {
			jo.put("Besoin",repository.save(besoin));
			return jo;
		}else {
			
			if(repository.getBesoinsByThemeNom(besoin.getTheme().getNom(), besoin.getIdUser()) != null) { 
				List<Module> modulesFromBesoin = repository.getBesoinsByThemeNom(besoin.getTheme().getNom() , besoin.getIdUser()).getTheme().getListModules();
				List<Module> arrayToAdd = new ArrayList<Module>();
				arrayToAdd.addAll(modulesFromBesoin);
				arrayToAdd.addAll(besoin.getTheme().getListModules());
				List<Module> disticnList = arrayToAdd.stream().filter(distinctByKey(m -> m.getNom())).collect(Collectors.toList());
				
				Besoins besoinToUpdate = repository.getBesoinsByThemeNom(besoin.getTheme().getNom() , besoin.getIdUser());
				besoinToUpdate.getTheme().setListModules(disticnList);
				jo.put("Besoin", repository.save(besoinToUpdate));
				return jo;
				
			}else {
				jo.put("Besoin",repository.save(besoin));
				return jo;
			}
			
		
		}

	}
	
	
	/*********************************** LISTE BESOINS ***************************************/
	public JSONObject getAllBesoins() {
		JSONObject jo = new JSONObject();
		if ( repository.findAll().size() != 0 ) {
			jo.put("Besoins" , repository.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des besoins est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UN BESOIN ***************************************/
	public JSONObject deleteBesoin(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {	
			repository.delete(repository.findById(id).get());
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
		if(repository.findById(id).isPresent()) {
			jo.put("Besoin", repository.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** VALIDER UN BESOIN PAR LE TEAMLEAD ***************************************/
	public JSONObject validerBesoinTL(int idBesoin , int trimestre , int idProjet , int idTL) {
		JSONObject jo = new JSONObject();
		if(repository.findById(idBesoin).isPresent()) {
			Besoins besoin = repository.findById(idBesoin).get();
			if(repositoryP.findById(idProjet).isPresent()) {
				Projet projet = repositoryP.findById(idProjet).get();
				besoin.setQuarter(trimestre);
				besoin.setValiderTL(true);
				besoin.setProjet(projet);				

				jo.put("Besoin", repository.save(besoin));
				return jo;
				
			}else {
				jo.put("Error" , "Projet n'existe pas !");
				return jo;
			}
		}else {
			jo.put("Error" , "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** ANNULER UNE VALIDATION D'UN BESOIN PAR LE TEAMLEAD ***************************************/
	public JSONObject annulerValidationBesoin (int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			Besoins besoin = repository.findById(id).get();
			besoin.setValiderTL(false);
			besoin.setQuarter(0);
			besoin.setProjet(null);
			jo.put("Besoin", repository.save(besoin));
			return jo;
		}else {
			jo.put("Error", "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** LIST BESOINS DES COLLABORATEURS PAR TEAM LEADER ***************************************/
	@SuppressWarnings( "rawtypes")
	public JSONObject getBesoinsByTL(int idTL) {
		
		JSONObject jo = new JSONObject();
		final String uri = "http://localhost:8383/collaborateurs/ByTL";
				
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
		map.add("id", idTL);
		HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
		ResponseEntity<JSONObject> response = restTemplate.postForEntity( uri, request , JSONObject.class );
		
		if(response.getBody().containsKey("Error")) {
			jo.put("Error" , response.getBody().get("Error"));
			return jo;
		}else {
			ArrayList listUsers = (ArrayList) response.getBody().get("Users");
			ArrayList<Besoins> listBesoinsTL = new ArrayList<Besoins>();			
			
			for (Object object : listUsers) {
				LinkedHashMap obj = (LinkedHashMap) object;
				LinkedHashMap user = (LinkedHashMap) obj.get("User");
				LinkedHashMap collaborateur = (LinkedHashMap) user.get("Collaborateur");
				int idCollaborateur = (int) collaborateur.get("idCollaborateur");
				
				List<Besoins> listBesoin = repository.getBesoinsByUser(idCollaborateur);
				for(Besoins besoin : listBesoin) {
					listBesoinsTL.add(besoin);
				}
			}
			List<Besoins> listTL = repository.getBesoinsByUser(idTL);
			listBesoinsTL.addAll(listTL);
			jo.put("BesoinsTL" , listBesoinsTL);
			
			return jo;
		}

	}
	
	/*********************************** LIST BESOINS PAR MANAGER ***************************************/
	@SuppressWarnings("unchecked")
	public JSONObject getBesoinsByManager(int idManager) {
		JSONObject jo = new JSONObject();
		List<Besoins> listAllBesoins = new ArrayList<Besoins>();
		List<TeamLead> listTL = repositoryTL.getTeamLeadByManager(idManager);
		for (TeamLead tl : listTL) {
			if( getBesoinsByTL(tl.getIdTeamLead()).containsKey("BesoinsTL") ) {
				listAllBesoins.addAll((Collection<? extends Besoins>) getBesoinsByTL(tl.getIdTeamLead()).get("BesoinsTL"));
			}
		}
		List<Besoins> finalList = new ArrayList<Besoins>();
		for (Besoins b : listAllBesoins) {
			if(b.isValiderTL()) {
				finalList.add(b);
			}
		}
		jo.put("BesoinsMG", finalList);
		return jo;
	}
	
	/*********************************** VALIDER UN BESOIN PAR LE MANAGER ***************************************/
	public JSONObject validerBesoinMG (int idBesoin) {
		JSONObject jo = new JSONObject();
		if(repository.findById(idBesoin).isPresent()) {
			Besoins besoin = repository.findById(idBesoin).get();
			besoin.setValiderMG(true);
			jo.put("Besoin",repository.save(besoin));
			return jo;
		}else {
			jo.put("Error", "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** ANNULER UN BESOIN PAR LE MANAGER ***************************************/
	public JSONObject annulerBesoinMG (int idBesoin) {
		JSONObject jo = new JSONObject();
		if(repository.findById(idBesoin).isPresent()) {
			Besoins besoin = repository.findById(idBesoin).get();
			besoin.setValiderMG(false);
			jo.put("Besoin" , repository.save(besoin));
			return jo;
		}else {
			jo.put("Error", "Besoin n'existe pas ! ");
			return jo; 
		}
	}
	
	/*********************************** BESOINS PAR USER ***************************************/
	public JSONObject getBesoinsByUser(int id) {
		JSONObject jo = new JSONObject();
		if( repository.getBesoinsByUser(id).size() != 0) {
			List<Besoins> listBesoins = repository.getBesoinsByUser(id);
			jo.put("Besoins" , listBesoins);
			return jo;
		}else {
			jo.put("Error", "Ce collaborateur n'a pas saisi des besoins ");
			return jo;
		}
	}
	

	
	
	
	
	 public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) 
	    {
	        Map<Object, Boolean> map = new ConcurrentHashMap<>();
	        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	    }
}














