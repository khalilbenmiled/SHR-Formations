package com.soprahr.Services;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
import com.soprahr.Repository.ProjetRepository;
import com.soprahr.Repository.TeamLeadRepository;
import com.soprahr.Utils.PROXY;
import com.soprahr.model.BU;
import com.soprahr.model.Besoins;
import com.soprahr.model.BesoinsPublier;
import com.soprahr.model.Participants;
import com.soprahr.model.Projet;
import com.soprahr.model.TeamLead;
import com.soprahr.model.TypeTheme;

import net.minidev.json.JSONObject;

@Service
public class BesoinsService {

	@Autowired
	public BesoinsRepository repository;
	@Autowired
	public ProjetRepository repositoryP;
	@Autowired
	public TeamLeadRepository repositoryTL;
	@Autowired
	public BesoinsPublierRepository repositoryBP;
	@Autowired
	public BesoinPublierServices serviceBP;
	@PersistenceContext
	public EntityManager em;
	
	
	/*********************************** AJOUTER UN BESOIN ***************************************/
	public JSONObject addBesoinByCollaborateur(Besoins besoin) {
		JSONObject jo = new JSONObject();
		
		if(verifyBesoinExist(besoin)) {
			jo.put("Error" , "Vous avez deja demandé ce besoin de formation");
			return jo;
		}else if (!verifyBesoinExist(besoin) && repository.getBesoinsByThemeNomAndNotPublish(besoin.getTheme().getNom(), besoin.getIdUser()) != null ) {
			
			if(repository.getBesoinsByThemeNomAndNotPublish(besoin.getTheme().getNom(), besoin.getIdUser()).isValiderTL() && !repository.getBesoinsByThemeNomAndNotPublish(besoin.getTheme().getNom(), besoin.getIdUser()).isSendToSF()) {
				jo.put("Error" , "Vous avez deja demandé ce besoin de formation");
				return jo;
			}else {
				
				Besoins besoinToUpdate = repository.getBesoinsByThemeNom(besoin.getTheme().getNom() , besoin.getIdUser());
				besoinToUpdate.getTheme().setListModules(besoin.getTheme().getListModules());
				besoinToUpdate.setValiderTL(false);
				besoinToUpdate.setValiderMG(false);
				besoinToUpdate.setListParticipants(besoin.getListParticipants());
								
				jo.put("Besoin", repository.save(besoinToUpdate));
				return jo;
			}
		
		}else {
			jo.put("Besoin", repository.save(besoin));
			return jo;
		}

	}
	
	/*********************************** AJOUTER UN BESOIN ***************************************/
	public JSONObject addBesoinByTeamLead(Besoins besoin) {
		JSONObject jo = new JSONObject();
		if(repository.getBesoinsByThemeNomAndNotPublish(besoin.getTheme().getNom(), besoin.getIdUser()) != null) {
			
			if(repository.getBesoinsByThemeNomAndNotPublish(besoin.getTheme().getNom(), besoin.getIdUser()).isValiderMG() && !repository.getBesoinsByThemeNomAndNotPublish(besoin.getTheme().getNom(), besoin.getIdUser()).isSendToSF()) {
				jo.put("Error" , "Vous avez deja demandé ce besoin de formation");
				return jo;
			}else {
				Besoins besoinToUpdate = repository.getBesoinsByThemeNomAndNotPublish(besoin.getTheme().getNom() , besoin.getIdUser());
				besoinToUpdate.getTheme().setListModules(besoin.getTheme().getListModules());
				besoinToUpdate.setValiderTL(true);
				besoinToUpdate.setValiderMG(false);
				besoinToUpdate.setQuarter(besoin.getQuarter());
				besoinToUpdate.setListParticipants(besoin.getListParticipants());
				besoinToUpdate.setProjet(besoin.getProjet());
				
				int annee = getAnnee(besoin.getQuarter());
				besoinToUpdate.setAnnee(annee);
				
				jo.put("Besoin", repository.save(besoinToUpdate));
				return jo;
			}

		}else {
			int annee = getAnnee(besoin.getQuarter());
			besoin.setAnnee(annee);
			jo.put("Besoin", repository.save(besoin));
			return jo;
		}
	}
	
	/*********************************** VERIFIER SI TEAMLEAD A SAISI UN BESOIN POUR LE COLLABORATEUR ***************************************/
	public boolean verifyBesoinExist(Besoins besoin) {
		List<Besoins> listBesoins = repository.findAll();
		for (Besoins b : listBesoins) {
			if(b.getTheme().getNom().equals(besoin.getTheme().getNom()) && !b.isSendToSF() && b.getListParticipants().stream().filter(p->p.getIdParticipant() == besoin.getIdUser()).findFirst().isPresent() && besoin.getIdUser() != b.getIdUser()) {
				return true ;
			}
		}
		return false;
	}
	
	/*********************************** MODIFIER UN BESOIN ***************************************/
	public JSONObject modifierBesoin(Besoins besoin) {
		JSONObject jo = new JSONObject();
		if(repository.findById(besoin.getId()).isPresent()) {
			Besoins b = repository.findById(besoin.getId()).get();
		

			if(besoin.getTheme().getNom() != null) {
				b.setTheme(besoin.getTheme());
			}
			if(besoin.getProjet().getNom() != null) {
				Projet p = repositoryP.findById(besoin.getProjet().getId()).get();
				b.setProjet(p);
			}
			if(besoin.getQuarter() != 0) {
				
				if(besoin.getQuarter() == -1) {
					b.setQuarter(0);
					b.setAnnee(0);
				}
				b.setQuarter(besoin.getQuarter());
				int annee = getAnnee(besoin.getQuarter());
				b.setAnnee(annee);
			}
			
			b.setValiderTL(besoin.isValiderTL());
			b.setValiderMG(besoin.isValiderMG());
			jo.put("Besoin", repository.save(b));
			return jo;
		}else {
			jo.put("Error", "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** SET ANNEE PAR RAPPORT TRIMESTRE ***************************************/
	public int getAnnee(int trimestre) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int month = c.get(Calendar.MONTH);
		int quarter = (month / 3) + 1;
		if(trimestre < quarter) {
			return c.get(Calendar.YEAR) + 1;
		}else {
			return c.get(Calendar.YEAR);
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
	
	/*********************************** SUPPRIMER UN BESOIN PAR COLLABORATEUR ***************************************/
	public JSONObject deleteBesoinByCollaborateur(int id) {
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
	
	/*********************************** SUPPRIMER UN BESOIN PAR MANAGER OU SERVICEFORMATION ***************************************/
	public JSONObject deleteBesoinByManagerOrSF(int id) {
		JSONObject jo = new JSONObject();
		
		if(repository.findById(id).isPresent()) {	
			Besoins besoin = repository.findById(id).get();
			BesoinsPublier besoinPublier = repositoryBP.getBesoinsPublierByThemeAndQuarter(besoin.getTheme().getNom(), besoin.getQuarter());

			
			if(besoinPublier != null) {	
				List<Besoins> listBesoins = besoinPublier.getListBesoins();
				listBesoins.remove(listBesoins.indexOf(besoin));
				
				if(listBesoins.size() == 0) {
					repositoryBP.delete(besoinPublier);
				}else {
					besoinPublier.setListBesoins(listBesoins);
					repositoryBP.save(besoinPublier);
				}
			}
			
			repository.delete(repository.findById(id).get());
			jo.put("Success", "Besoin supprimé");
			return jo;
		}else {
			jo.put("Error" , "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UN BESOIN ***************************************/
	public JSONObject deleteBesoin(int id) {
		JSONObject jo = new JSONObject();
		
		if(repository.findById(id).isPresent()) {	
			Besoins besoin = repository.findById(id).get();
			
		
			BesoinsPublier besoinPublier = repositoryBP.getBesoinsPublierByThemeAndQuarter(besoin.getTheme().getNom(), besoin.getQuarter());
			List<Besoins> listBesoins = besoinPublier.getListBesoins();
			listBesoins.remove(listBesoins.indexOf(besoin));
			
			if(listBesoins.size() == 0) {
				repositoryBP.delete(besoinPublier);
			}else {
				besoinPublier.setListBesoins(listBesoins);
				repositoryBP.save(besoinPublier);
			}

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
	public JSONObject validerBesoinTL(int idBesoin , int trimestre , int idProjet , boolean validerMG ) {
		JSONObject jo = new JSONObject();
		if(repository.findById(idBesoin).isPresent()) {
			Besoins besoin = repository.findById(idBesoin).get();
			if(repositoryP.findById(idProjet).isPresent()) {
				Projet projet = repositoryP.findById(idProjet).get();
				besoin.setQuarter(trimestre);
				besoin.setValiderTL(true);
				besoin.setProjet(projet);	
				besoin.setValiderMG(validerMG);
				besoin.setNbrPrevu(1);
				
				int annee = getAnnee(trimestre);
				besoin.setAnnee(annee);
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
			besoin.setValiderMG(false);
			besoin.setAnnee(0);
			jo.put("Besoin", repository.save(besoin));
			return jo;
		}else {
			jo.put("Error", "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** LIST BESOINS DES COLLABORATEURS PAR TEAM LEADER FOR MANAGER***************************************/
	@SuppressWarnings( "rawtypes")
	public JSONObject getBesoinsByTLForManager(int idTL) {
		
		JSONObject jo = new JSONObject();
		final String uri = PROXY.Collaborateurs+"/collaborateurs/ByTL";
				
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
				
				List<Besoins> listBesoin = repository.getBesoinsByUserForManager(idCollaborateur);
				for(Besoins besoin : listBesoin) {
					listBesoinsTL.add(besoin);
				}
			}
			List<Besoins> listTL = repository.getBesoinsByUserForManager(idTL);
			listBesoinsTL.addAll(listTL);
			jo.put("BesoinsTL" , listBesoinsTL);
			
			return jo;
		}

	}
	
	/*********************************** LIST BESOINS DES COLLABORATEURS PAR TEAM LEADER ***************************************/
	@SuppressWarnings( "rawtypes")
	public JSONObject getBesoinsByTL(int idTL) {
		
		JSONObject jo = new JSONObject();
		final String uri = PROXY.Collaborateurs+"/collaborateurs/ByTL";
				
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
			if( getBesoinsByTLForManager(tl.getIdTeamLead()).containsKey("BesoinsTL") ) {
				listAllBesoins.addAll((Collection<? extends Besoins>) getBesoinsByTLForManager(tl.getIdTeamLead()).get("BesoinsTL"));
			}
		}

		jo.put("BesoinsMG", listAllBesoins);
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
		if( repository.getBesoinsByUser(id).size() != 0 || repository.findAll().size() != 0) {
			List<Besoins> finalList = new ArrayList<Besoins>();
			List<Besoins> listBesoins = repository.getBesoinsByUser(id);
			finalList.addAll(listBesoins);
			
			
			List<Besoins> allBesoins = repository.findAllNotPublish();
			for (Besoins besoin : allBesoins) {
				for(Participants participant : besoin.getListParticipants()) {
					if(participant.getIdParticipant() == id && besoin.getIdUser() != id) {
						finalList.add(besoin);
					}
				}
			}
			
			jo.put("Besoins" , finalList);
			return jo;
		}else {
			jo.put("Error", "Ce collaborateur n'a pas saisi des besoins ");
			return jo;
		}
	}
	
	/*********************************** BESOINS PAR USER FOR MANAGER ***************************************/
	public JSONObject getBesoinsByUserForManager(int id) {
		JSONObject jo = new JSONObject();
		if( repository.getBesoinsByUser(id).size() != 0 || repository.findAll().size() != 0) {
			List<Besoins> finalList = new ArrayList<Besoins>();
			List<Besoins> listBesoins = repository.getBesoinsByUserForManager(id);
			finalList.addAll(listBesoins);
			
			
			List<Besoins> allBesoins = repository.findAll();
			for (Besoins besoin : allBesoins) {
				for(Participants participant : besoin.getListParticipants()) {
					if(participant.getIdParticipant() == id && besoin.getIdUser() != id) {
						finalList.add(besoin);
					}
				}
			}
			
			jo.put("Besoins" , finalList);
			return jo;
		}else {
			jo.put("Error", "Ce collaborateur n'a pas saisi des besoins ");
			return jo;
		}
	}
	
	/*********************************** RAPPORT BESOINS ***************************************/
	@SuppressWarnings({ "rawtypes" })
	public JSONObject rapportsBesoins(String nomTheme , String typeTheme , int quarter , int idProjet ,String validerTL , String validerMG , String bu) {
		JSONObject jo = new JSONObject();
		List<Besoins> listBesoins = repository.findAll();
		
		List<Predicate<Besoins>> allPredicates = new ArrayList<Predicate<Besoins>>();
		if(nomTheme != "") {
			allPredicates.add(b -> b.getTheme().getNom().equals(nomTheme));
		}
		if(quarter != 0) {
			allPredicates.add(b -> b.getQuarter() == quarter);
		}
		if(typeTheme != "") {
			Enum type = Enum.valueOf(TypeTheme.class, typeTheme);
			allPredicates.add(b -> b.getTheme().getType().equals(type));
		}
		if(idProjet != 0) {
			Projet p = repositoryP.findById(idProjet).get();
			allPredicates.add(b -> b.getProjet() == p);
		}
		if(validerMG != "") {
			boolean bool = Boolean.parseBoolean(validerMG);
			allPredicates.add(b -> b.isValiderMG() == bool);
		}
		if(validerTL != "") {
			boolean bool = Boolean.parseBoolean(validerTL);
			allPredicates.add(b -> b.isValiderTL() == bool);
		}
		if(bu != "") {	
			Enum bUnit = Enum.valueOf(BU.class, bu);
			allPredicates.add(b -> b.getBu().equals(bUnit));
		}
		
	
		
		List<Besoins> result = listBesoins.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());

				Map<Object, Map<Object, List<Besoins>>> result2 = result.stream().collect(
				Collectors.groupingBy(b -> b.getTheme().getNom(), 
				Collectors.groupingBy(be -> be.getQuarter() 
				
				)
				));
				
		jo.put("RapportsBesoins", result2);
		return jo;
	

	}
	
	/*********************************** RAPPORT BESOINS PAR TEAM LEAD ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONObject rapportsBesoinsByTL (int idTL,String nomTheme , String typeTheme, int quarter, int idProjet , String validerTL , String validerMG) {
		JSONObject jo = new JSONObject();
		List<Besoins> listBesoins = new ArrayList<Besoins>();
		if( getBesoinsByTL(idTL).containsKey("BesoinsTL") ) {
			listBesoins.addAll((Collection<? extends Besoins>) getBesoinsByTL(idTL).get("BesoinsTL"));
		}
		List<Predicate<Besoins>> allPredicates = new ArrayList<Predicate<Besoins>>();
		if(nomTheme != "") {
			allPredicates.add(b -> b.getTheme().getNom().equals(nomTheme));
		}
		if(quarter != 0) {
			allPredicates.add(b -> b.getQuarter() == quarter);
		}
		if(typeTheme != "") {
			Enum type = Enum.valueOf(TypeTheme.class, typeTheme);
			allPredicates.add(b -> b.getTheme().getType().equals(type));
		}
		if(idProjet != 0) {
			Projet p = repositoryP.findById(idProjet).get();
			allPredicates.add(b -> b.getProjet() == p);
		}
		if(validerMG != "") {
			boolean bool = Boolean.parseBoolean(validerMG);
			allPredicates.add(b -> b.isValiderMG() == bool);
		}
		if(validerTL != "") {
			boolean bool = Boolean.parseBoolean(validerTL);
			allPredicates.add(b -> b.isValiderTL() == bool);
		}
	
		
		List<Besoins> result = listBesoins.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());

		
		
			
				Map<Object, Map<Object, List<Besoins>>> result2 = result.stream().collect(
				Collectors.groupingBy(b -> b.getTheme().getNom(), 
				Collectors.groupingBy(be -> be.getQuarter() 
				
				)
				));
				
		jo.put("RapportsBesoinsTL", result2);
		return jo;
	
		
	}

	
	/*********************************** RAPPORT BESOINS PAR MANAGER ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONObject rapportsBesoinsByMG (int idManager,String nomTheme , String typeTheme, int quarter, int idProjet , String validerMG ) {
		JSONObject jo = new JSONObject();
		List<Besoins> listBesoins = new ArrayList<Besoins>();
		if( getBesoinsByManager(idManager).containsKey("BesoinsMG") ) {
			listBesoins.addAll((Collection<? extends Besoins>) getBesoinsByManager(idManager).get("BesoinsMG"));
		}
		List<Predicate<Besoins>> allPredicates = new ArrayList<Predicate<Besoins>>();
		if(nomTheme != "") {
			allPredicates.add(b -> b.getTheme().getNom().equals(nomTheme));
		}
		if(quarter != 0) {
			allPredicates.add(b -> b.getQuarter() == quarter);
		}
		if(typeTheme != "") {
			Enum type = Enum.valueOf(TypeTheme.class, typeTheme);
			allPredicates.add(b -> b.getTheme().getType().equals(type));
		}
		if(idProjet != 0) {
			Projet p = repositoryP.findById(idProjet).get();
			allPredicates.add(b -> b.getProjet() == p);
		}
		if(validerMG != "") {
			boolean bool = Boolean.parseBoolean(validerMG);
			allPredicates.add(b -> b.isValiderMG() == bool);
		}
		
		List<Besoins> result = listBesoins.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());

		
			
				Map<Object, Map<Object, List<Besoins>>> result2 = result.stream().collect(
				Collectors.groupingBy(b -> b.getTheme().getNom(), 
				Collectors.groupingBy(be -> be.getQuarter() 
				
				)
				));
				
		jo.put("RapportsBesoinsMG", result2);
		return jo;
	
		
	}
	
	/*********************************** SET BESOINS PLANIFIER ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject setBesoinPlanifier(int id , ArrayList arrayParticipants) {
		JSONObject jo = new JSONObject();
		
		if(repository.findById(id).isPresent()) {
			Besoins besoin = repository.findById(id).get();
			
			List<Participants> listParticipants = new ArrayList<Participants>();
			for(Object object : arrayParticipants) {
				LinkedHashMap obj = (LinkedHashMap) object;
				int idParticipant = (int) obj.get("id");
				Participants participant = new Participants();
				participant.setIdParticipant(idParticipant);
				participant.setParticipe(true);
				listParticipants.add(participant);	
			}
			List<Participants> newList = new ArrayList<Participants>();
			for(Participants partFromBesoin : besoin.getListParticipants()) {
				for(Participants partFromSaisie : listParticipants) {
					if(partFromBesoin.getIdParticipant() == partFromSaisie.getIdParticipant()) {
						partFromBesoin.setParticipe(true);
					}
				}
				newList.add(partFromBesoin);
			}
			besoin.setListParticipants(newList);
			for(Participants p : newList) {
				if(!p.isParticipe()) {
					besoin.setPlanifier(false);
					jo.put("Besoin", repository.save(besoin));
					return jo;	
				}
			}
			besoin.setPlanifier(true);
			
			BesoinsPublier besoinPublier = repositoryBP.getBesoinsPublierByThemeAndQuarter(besoin.getTheme().getNom(), besoin.getQuarter());
			List<Besoins> listBesoins = besoinPublier.getListBesoins();
			listBesoins.remove(listBesoins.indexOf(besoin));
			if(listBesoins.size() == 0) {
				repositoryBP.delete(besoinPublier);
			}else {
				besoinPublier.setListBesoins(listBesoins);
				repositoryBP.save(besoinPublier);
			}
			
			
			jo.put("Besoin", repository.save(besoin));
			return jo;
			
//			List<Participants> newList = new ArrayList<Participants>();
//			for(Participants p1 : besoin.getListParticipants()) {
//				
//				for (Participants p2 : listParticipants) {
//					if(p1.getIdParticipant() == p2.getIdParticipant()) {
//						p1.setParticipe(true);
//					}
//				}
//				newList.add(p1);
//			}						
//			
//			besoin.setListParticipants(newList);
//			
//			for(Participants p : newList) {
//				if(!p.isParticipe()) {
//					besoin.setPlanifier(false);
//					jo.put("Besoin", repository.save(besoin));
//					return jo;
//				}
//			}
//			besoin.setPlanifier(true);
//			jo.put("Besoin", repository.save(besoin));
//			return jo;
			
		}else {
			jo.put("Error", "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** LIST PARTICIPANTS BESOIN ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject getListParticipantBesoin(int idBesoin) {
		JSONObject jo = new JSONObject();
		List<Object> tabs = new ArrayList<Object>();
		if(repository.findById(idBesoin).isPresent()) {
			int idUser = repository.findById(idBesoin).get().getIdUser();
			ResponseEntity<JSONObject> user = getUserAPI(PROXY.Utilisateurs+"/users/byId", idUser);
			if(user.getBody().containsKey("Error")) {
				jo.put("Error" , user.getBody().get("Error"));
				return jo;
			}else {
							
		
					List<Participants> listParticipants = repository.findById(idBesoin).get().getListParticipants();
					for (Participants participant : listParticipants) {
						if(!participant.isParticipe()) {
							ResponseEntity<JSONObject> part = getUserAPI(PROXY.Utilisateurs+"/users/byId", participant.getIdParticipant());
							LinkedHashMap partBody = (LinkedHashMap) part.getBody().get("User");
							tabs.add(partBody);
						}
					
					}
					jo.put("Participants", tabs);
					return jo;
			
			}
			
		}else {
			jo.put("Error" , "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** LIST PARTICIPANTS FORMATIONS ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject getListParticipantFormation(String theme , int quarter) {
		JSONObject jo = new JSONObject();
		List<Object> tabs = new ArrayList<Object>();
		BesoinsPublier besoinPublier = repositoryBP.getBesoinsPublierByThemeAndQuarterTF(theme, quarter);
		if(besoinPublier != null) {
			List<Besoins> listBesoins = besoinPublier.getListBesoins();
			if(listBesoins.size() != 0) {
				for (Besoins besoin : listBesoins) {
					ResponseEntity<JSONObject> user = getUserAPI(PROXY.Utilisateurs+"/users/byId", besoin.getIdUser());
					
					if(user.getBody().containsKey("Error")) {
						jo.put("Error" , user.getBody().get("Error"));
						return jo;
					}else {
						LinkedHashMap userBody = (LinkedHashMap) user.getBody().get("User");
						if(userBody.get("role").equals("TEAMLEAD")) {
							
							for (Participants participant : besoin.getListParticipants()) {
								ResponseEntity<JSONObject> collaborateur = getUserAPI(PROXY.Utilisateurs+"/users/byId", participant.getIdParticipant());
								if(collaborateur.getBody().containsKey("Error")) {
									jo.put("Error" , collaborateur.getBody().get("Error"));
									return jo;
								}else {
									LinkedHashMap collaborateurBody = (LinkedHashMap) collaborateur.getBody().get("User");
									tabs.add(collaborateurBody);
								}
							}
						}else {
							tabs.add(userBody);
						}
					}
					
				}
				jo.put("UsersInfos" , tabs);
				return jo;
			}else {
				jo.put("Error", "La liste des besoins est vide ");
				return jo;
			}
		}else {
			jo.put("Error", "Besoin n'existe pas " );
			return jo;
		}
		
	}
	
	/*********************************** DELETE BESOIN FROM BESOIN PUBLIER ***************************************/
	public JSONObject deleteBesoinFromBesoinPublier (int idB , int idBP) {
		JSONObject jo = new JSONObject();
		if(repository.findById(idB).isPresent()) {
			Besoins besoin = repository.findById(idB).get();
			repository.delete(besoin);
			if(repositoryBP.findById(idBP).isPresent()) {
				List<Besoins> list = new ArrayList<Besoins>();
				for(Besoins besoins : repositoryBP.findById(idBP).get().getListBesoins()) {
					if(besoins.getId() != idB) {
						list.add(besoins);
					}
				}
				BesoinsPublier besoinPublier = repositoryBP.findById(idBP).get();
				if(list.size() != 0) {
					besoinPublier.setListBesoins(list);
					
					jo.put("BesoinPublier", repositoryBP.save(besoinPublier));
					return jo;
				}else {
					
					repositoryBP.delete(besoinPublier);
					jo.put("Delete","Delete success");
					return jo;
				}
			}else {
				jo.put("Error", "Besoin Publier n'existe pas " );
				return jo;
			}
		}else {
			jo.put("Error", "Besoin n'existe pas " );
			return jo;
		}
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
	
	
	 public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) 
	    {
	        Map<Object, Boolean> map = new ConcurrentHashMap<>();
	        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	    }
}














