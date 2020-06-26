package com.soprahr.Services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.soprahr.Repository.BesoinsPublierRepository;
import com.soprahr.Repository.BesoinsRepository;
import com.soprahr.Utils.PROXY;
import com.soprahr.model.Besoins;
import com.soprahr.model.BesoinsPublier;


import net.minidev.json.JSONObject;

@Service
public class BesoinPublierServices {

	@Autowired
	public BesoinsPublierRepository repository;
	@Autowired
	public BesoinsRepository repositoryB;
	@PersistenceContext
	EntityManager em;
	
	/*********************************** AJOUTER UN BESOIN POUR LE PUBLIER ***************************************/
	public JSONObject addBesoinsPublier(int idBesoin , int idManager) {
		JSONObject jo = new JSONObject();
		if(repositoryB.findById(idBesoin).isPresent()) {
			Besoins besoin = repositoryB.findById(idBesoin).get();
			besoin.setPublier(true);
			repositoryB.save(besoin);
			String nomTheme = besoin.getTheme().getNom();
			int quarter = besoin.getQuarter();
			
			BesoinsPublier besoinPublier = repository.getBesoinsPublierByThemeAndQuarterAndManager(nomTheme, quarter , idManager);
			
			if(besoinPublier == null) {
				BesoinsPublier newBesoins = new BesoinsPublier();
				newBesoins.setIdManager(idManager);
				newBesoins.setTheme(nomTheme);
				newBesoins.setPublier(false);
				newBesoins.setQuarter(quarter);
				List<Besoins> listBesoins = new ArrayList<Besoins>();
				listBesoins.add(besoin);
				newBesoins.setListBesoins(listBesoins);
				jo.put("BesoinPublier",repository.save(newBesoins));
				return jo;
			}else if(besoinPublier != null && besoinPublier.isPublier()) {
				BesoinsPublier newBesoins = new BesoinsPublier();
				newBesoins.setIdManager(idManager);
				newBesoins.setTheme(nomTheme);
				newBesoins.setPublier(false);
				newBesoins.setQuarter(quarter);
				List<Besoins> listBesoins = new ArrayList<Besoins>();
				listBesoins.add(besoin);
				newBesoins.setListBesoins(listBesoins);
				jo.put("BesoinPublier",repository.save(newBesoins));
				return jo;
			}else {
				List<Besoins> listBesoins = besoinPublier.getListBesoins();
				listBesoins.add(besoin);
				besoinPublier.setListBesoins(listBesoins);
				jo.put("BesoinPublier",repository.save(besoinPublier));
				return jo;
			}
		}else {
			jo.put("Error" , "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RETIRER UN BESOIN AVANT LE PUBLIER ***************************************/
	public JSONObject retirerBesoinPublier(int idBesoin , int idManager) {
		JSONObject jo = new JSONObject();
		if(repositoryB.findById(idBesoin).isPresent()) {
			Besoins besoin = repositoryB.findById(idBesoin).get();
			besoin.setPublier(false);
			repositoryB.save(besoin);
			BesoinsPublier besoinPublier = repository.getBesoinsPublierByThemeAndQuarterAndManager(besoin.getTheme().getNom(), besoin.getQuarter() , idManager);
			List<Besoins> listBesoins = besoinPublier.getListBesoins();
			listBesoins.remove(listBesoins.indexOf(besoin));
			if(listBesoins.size() == 0) {
				repository.delete(besoinPublier);
				jo.put("Success" , besoinPublier.getId());
				return jo;
			}else {
				besoinPublier.setListBesoins(listBesoins);
				jo.put("BesoinPublier", repository.save(besoinPublier));
				return jo;
			}
		
		}else {
			jo.put("Error", "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** LIST BESOINS A PUBLIER ***************************************/
	public JSONObject listBesoinsPublier(int id) {
		JSONObject jo = new JSONObject();
		if (repository.getAllNotPublish(id).size() != 0 ) {			
			jo.put("BesoinsPublier", repository.getAllNotPublish(id));
			return jo;
		}else {
			jo.put("Error", "La listes des besoins à publier");
			return jo;
		}
	}
	
	/*********************************** LIST BESOINS ***************************************/
	public JSONObject listBesoins() {
		JSONObject jo = new JSONObject();
		List<BesoinsPublier> newList = new ArrayList<BesoinsPublier>();
		if (repository.findAll().size() != 0 ) {
			List<BesoinsPublier> listBesoinsPublier = repository.getAllPublish();

			for(BesoinsPublier besoinPublier : listBesoinsPublier) {
				List<Besoins> listBesoins = besoinPublier.getListBesoins();
				List<Besoins> newListBesoins = new ArrayList<Besoins>();
				
				for(Besoins besoin : listBesoins) {
					if(!besoin.isPlanifier()) {
						newListBesoins.add(besoin);
					}
				}
				besoinPublier.setListBesoins(newListBesoins);
				if(besoinPublier.getListBesoins().size() != 0) {
					newList.add(besoinPublier);
				}	
			}
			jo.put("Besoins", newList);
			return jo;
		}else {
			jo.put("Error", "La listes des besoins à publier");
			return jo;
		}
	}
	
	/*********************************** PUBLIER UN BESOIN  ***************************************/
	public JSONObject publierBesoin(int id) {
		JSONObject jo = new JSONObject();
		if( repository.findById(id).isPresent() ) {
			BesoinsPublier besoinPublier = repository.findById(id).get();	
			BesoinsPublier besoinExist = repository.checkIfExist(besoinPublier.getTheme(), besoinPublier.getQuarter() , id);
			
			if(besoinExist != null) {
				for(Besoins b : besoinPublier.getListBesoins()) {
					b.setPublier(true);
					b.setSendToSF(true);
					repositoryB.save(b);
				}
				List<Besoins> list = besoinExist.getListBesoins();
				list.addAll(besoinPublier.getListBesoins());
				besoinExist.setListBesoins(list);
				
				repository.delete(besoinPublier);
				jo.put("BesoinPublier", repository.save(besoinExist));
				return jo;
			}else {
				for(Besoins b : besoinPublier.getListBesoins()) {
					b.setPublier(true);
					b.setSendToSF(true);
					repositoryB.save(b);
				}
				besoinPublier.setPublier(true);
				jo.put("BesoinPublier", repository.save(besoinPublier));
				return jo;
			}

		}else {
			jo.put("Error", "BesoinPublier n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** USER INFO BESOIN PUBLIER ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject getUserInfos(int id) {
		JSONObject jo = new JSONObject();
		LinkedHashMap user = null;
		LinkedHashMap teamlead = null;
		LinkedHashMap manager = null;
		ResponseEntity<JSONObject> infosCollaborateur = getUserAPI(PROXY.Utilisateurs+"/users/byId", id);
		
		if(infosCollaborateur.getBody().containsKey("Error")) {
			jo.put("Error" , infosCollaborateur.getBody().get("Error"));
			return jo;
		}else {
			user = (LinkedHashMap) infosCollaborateur.getBody().get("User");
			if (user.get("role").equals("COLLABORATEUR") ) {
				ResponseEntity<JSONObject> collaborateurAPI = getUserAPI(PROXY.Collaborateurs+"/collaborateurs/byID", (int)user.get("id"));		
				if(collaborateurAPI.getBody().containsKey("Error")) {
					jo.put("Error" , collaborateurAPI.getBody().get("Error"));
					return jo;
				}else {		
					LinkedHashMap collaborateur = (LinkedHashMap) collaborateurAPI.getBody().get("Collaborateur");
					int idTeamLeader = (int) collaborateur.get("idTeamLeader");
					ResponseEntity<JSONObject> infosTeamLeader = getUserAPI(PROXY.Utilisateurs+"/users/byId", idTeamLeader);
					if(infosTeamLeader.getBody().containsKey("Error")) {
						jo.put("Error" , infosTeamLeader.getBody().get("Error"));
						return jo;
					}else {
						teamlead = (LinkedHashMap) infosTeamLeader.getBody().get("User");
						ResponseEntity<JSONObject> teamLeaderAPI  = getUserAPI(PROXY.Besoins+"/teamlead/byID" , idTeamLeader);
						if(teamLeaderAPI.getBody().containsKey("Error")) {
							jo.put("Error" , teamLeaderAPI.getBody().get("Error"));
							return jo;
						}else {
							LinkedHashMap tl = (LinkedHashMap) teamLeaderAPI.getBody().get("TeamLead");
							ResponseEntity<JSONObject> infosMannager = getUserAPI(PROXY.Utilisateurs+"/users/byId", (int) tl.get("idManager"));
							manager = (LinkedHashMap) infosMannager.getBody().get("User");
						}
					}		
				}
				
			}else {
				teamlead = user;
				user = null;
				ResponseEntity<JSONObject> teamLeaderAPI  = getUserAPI(PROXY.Besoins+"/teamlead/byID" , (int) teamlead.get("id"));
				if(teamLeaderAPI.getBody().containsKey("Error")) {
					jo.put("Error" , teamLeaderAPI.getBody().get("Error"));
					return jo;
				}else {
					LinkedHashMap tl = (LinkedHashMap) teamLeaderAPI.getBody().get("TeamLead");
					ResponseEntity<JSONObject> infosMannager = getUserAPI(PROXY.Utilisateurs+"/users/byId", (int) tl.get("idManager"));
					manager = (LinkedHashMap) infosMannager.getBody().get("User");
				}
			}
		}

		jo.put("Collaborateur" , user);
		jo.put("TeamLead" , teamlead);
		jo.put("Manager" , manager);
		return jo;
	}
	
	
	/*********************************** API USER BY ID ***************************************/
	public ResponseEntity<JSONObject> getUserAPI(String uri , int id) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
		map.add("id", id);
		HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
		ResponseEntity<JSONObject> response = restTemplate.postForEntity( uri, request , JSONObject.class );
		return response;
	}
	
	
	
	
}
