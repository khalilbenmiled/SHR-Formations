package com.soprahr.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import com.soprahr.Repository.TeamLeadRepository;
import com.soprahr.Utils.PROXY;
import com.soprahr.model.BU;
import com.soprahr.model.Besoins;
import com.soprahr.model.TeamLead;
import com.soprahr.model.TypeTheme;
import net.minidev.json.JSONObject;

@Service
public class ReportingBesoinsService {

	@Autowired
	public BesoinsRepository repository;
	@Autowired
	public TeamLeadRepository repositoryTL;
		
	/*********************************** BESOINS DEMANDER PAR FILTER SERVICEFORMATION ***************************************/
	@SuppressWarnings({ "rawtypes" })
	public JSONObject filterFormationDemander(String bu , int quarter , String theme , int annee) {
		JSONObject jo = new JSONObject();
		List<Besoins> listBesoins = repository.findAll();
		List<Besoins> listBesoinsPlanifier = repository.getBesoinPlanifier();
		List<Besoins> listBesoinsNonPlanifier = repository.getBesoinNonPlanifier();
		
		List<Predicate<Besoins>> allPredicates = new ArrayList<Predicate<Besoins>>();
		if(bu != "") {
			Enum enumBU = Enum.valueOf(BU.class, bu);
			allPredicates.add(b -> b.getBu().equals(enumBU));
		}
		if(quarter != 0) {
			allPredicates.add(b -> b.getQuarter() == quarter);
		}
		if(annee != 0) {
			allPredicates.add(b -> b.getAnnee() == annee);
		}
		if(theme != "") {
			allPredicates.add(b -> b.getTheme().getNom().equals(theme));
		}
		
		
			List<Besoins> result = listBesoins.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
			List<Besoins> resultPlanifier = listBesoinsPlanifier.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
			List<Besoins> resultNonPlanifier = listBesoinsNonPlanifier.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
			
			Map<Object, List<Besoins>> map = result.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
			Map<Object, List<Besoins>> mapPlanifier = resultPlanifier.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
			Map<Object, List<Besoins>> mapNonPlanifier = resultNonPlanifier.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
			
			Map<Object,JSONObject> all = new HashMap<Object, JSONObject>();
		
			for(Map.Entry<Object, List<Besoins>> entry : map.entrySet()) {
				Object nomTheme = entry.getKey();			
				
				JSONObject json = new JSONObject();
				json.put("Results", entry.getValue());
				json.put("ResultsPlanifier", mapPlanifier.get(nomTheme));
				json.put("ResultsNonPlanifier", mapNonPlanifier.get(nomTheme));
				all.put(nomTheme, json);
			}
			
			jo.put("Results", all);
			return jo;
		
	}
	
	/*********************************** BESOINS DEMANDER PAR FILTER COLLABORATEUR ***************************************/
	@SuppressWarnings({ "rawtypes" })
	public JSONObject filterFormationDemanderParCollaborateur(String bu , int quarter , String theme , int idCollaborateur,int annee) {
		JSONObject jo = new JSONObject();
		List<Besoins> listBesoins = repository.getAllBesoinsByCollaborateur(idCollaborateur);
		List<Besoins> listBesoinsPlanifier = repository.getBesoinPlanifierByCollaborateur(idCollaborateur);
		List<Besoins> listBesoinsNonPlanifier = repository.getBesoinNonPlanifierByCollaborateur(idCollaborateur);
		
		List<Predicate<Besoins>> allPredicates = new ArrayList<Predicate<Besoins>>();
		if(bu != "") {
			Enum enumBU = Enum.valueOf(BU.class, bu);
			allPredicates.add(b -> b.getBu().equals(enumBU));
		}
		if(quarter != 0) {
			allPredicates.add(b -> b.getQuarter() == quarter);
		}
		if(annee != 0) {
			allPredicates.add(b -> b.getAnnee() == annee);
		}
		if(theme != "") {
			allPredicates.add(b -> b.getTheme().getNom().equals(theme));
		}
		
			List<Besoins> result = listBesoins.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
			List<Besoins> resultPlanifier = listBesoinsPlanifier.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
			List<Besoins> resultNonPlanifier = listBesoinsNonPlanifier.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
			
			Map<Object, List<Besoins>> map = result.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
			Map<Object, List<Besoins>> mapPlanifier = resultPlanifier.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
			Map<Object, List<Besoins>> mapNonPlanifier = resultNonPlanifier.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
			
			Map<Object,JSONObject> all = new HashMap<Object, JSONObject>();
		
			for(Map.Entry<Object, List<Besoins>> entry : map.entrySet()) {
				Object nomTheme = entry.getKey();			
				
				JSONObject json = new JSONObject();
				json.put("Results", entry.getValue());
				json.put("ResultsPlanifier", mapPlanifier.get(nomTheme));
				json.put("ResultsNonPlanifier", mapNonPlanifier.get(nomTheme));
				all.put(nomTheme, json);
			}
			
			jo.put("Results", all);
			return jo;
		
	}
	
	/*********************************** BESOINS DEMANDER PAR FILTER TEAMLEAD ***************************************/
	@SuppressWarnings({ "rawtypes" })
	public JSONObject filterFormationDemanderParTeamLead(String bu , int quarter , String theme , int idTeamLead, int annee) {
		JSONObject jo = new JSONObject();
		
		
		final String uri = PROXY.Collaborateurs+"/collaborateurs/ByTL";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
		map.add("id", idTeamLead);
		HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
		ResponseEntity<JSONObject> response = restTemplate.postForEntity( uri, request , JSONObject.class );
		
		ArrayList listUsers = (ArrayList) response.getBody().get("Users");
		List<Besoins> allBesoins = new ArrayList<Besoins>();
		List<Besoins> allBesoinsPlanifier = new ArrayList<Besoins>();
		List<Besoins> allBesoinsNonPlanifier = new ArrayList<Besoins>();
		
		for (Object object : listUsers) {
			LinkedHashMap obj = (LinkedHashMap) object;
			LinkedHashMap user = (LinkedHashMap) obj.get("User");
			LinkedHashMap collaborateur = (LinkedHashMap) user.get("Collaborateur");
			int idCollaborateur = (int) collaborateur.get("idCollaborateur");
			
			allBesoins.addAll(repository.getAllBesoinsByCollaborateur(idCollaborateur));
			allBesoinsPlanifier.addAll(repository.getBesoinPlanifierByCollaborateur(idCollaborateur));
			allBesoinsNonPlanifier.addAll(repository.getBesoinNonPlanifierByCollaborateur(idCollaborateur));
		}
			
			allBesoins.addAll(repository.getAllBesoinsByCollaborateur(idTeamLead));
			allBesoinsPlanifier.addAll(repository.getBesoinPlanifierByCollaborateur(idTeamLead));
			allBesoinsNonPlanifier.addAll(repository.getBesoinNonPlanifierByCollaborateur(idTeamLead));
			
			List<Predicate<Besoins>> allPredicates = new ArrayList<Predicate<Besoins>>();
			if(bu != "") {
				Enum enumBU = Enum.valueOf(BU.class, bu);
				allPredicates.add(b -> b.getBu().equals(enumBU));
			}
			if(quarter != 0) {
				allPredicates.add(b -> b.getQuarter() == quarter);
			}
			if(annee != 0) {
				allPredicates.add(b -> b.getAnnee() == annee);
			}
			if(theme != "") {
				allPredicates.add(b -> b.getTheme().getNom().equals(theme));
			}
			
				List<Besoins> result = allBesoins.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
				List<Besoins> resultPlanifier = allBesoinsPlanifier.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
				List<Besoins> resultNonPlanifier = allBesoinsNonPlanifier.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
				
				Map<Object, List<Besoins>> mapp = result.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
				Map<Object, List<Besoins>> mapPlanifier = resultPlanifier.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
				Map<Object, List<Besoins>> mapNonPlanifier = resultNonPlanifier.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
				
				Map<Object,JSONObject> all = new HashMap<Object, JSONObject>();
			
				for(Map.Entry<Object, List<Besoins>> entry : mapp.entrySet()) {
					Object nomTheme = entry.getKey();			
					
					JSONObject json = new JSONObject();
					json.put("Results", entry.getValue());
					json.put("ResultsPlanifier", mapPlanifier.get(nomTheme));
					json.put("ResultsNonPlanifier", mapNonPlanifier.get(nomTheme));
					all.put(nomTheme, json);
				}
				
				jo.put("Results", all);
				return jo;

		
	}
	
	/*********************************** BESOINS DEMANDER PAR FILTER MANAGER ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject filterFormationDemanderParManager(String bu , int quarter , String theme , int idManager, int annee) {
		JSONObject jo = new JSONObject();
		List<TeamLead> listTL = repositoryTL.getTeamLeadByManager(idManager);
		
		List<Besoins> allBesoins = new ArrayList<Besoins>();
		List<Besoins> allBesoinsPlanifier = new ArrayList<Besoins>();
		List<Besoins> allBesoinsNonPlanifier = new ArrayList<Besoins>();
		for (TeamLead tl : listTL) {
			
			final String uri = PROXY.Collaborateurs+"/collaborateurs/ByTL";
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
			map.add("id", tl.getIdTeamLead());
			HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
			ResponseEntity<JSONObject> response = restTemplate.postForEntity( uri, request , JSONObject.class );
			
			ArrayList listUsers = (ArrayList) response.getBody().get("Users");
			
			for (Object object : listUsers) {
				LinkedHashMap obj = (LinkedHashMap) object;
				LinkedHashMap user = (LinkedHashMap) obj.get("User");
				LinkedHashMap collaborateur = (LinkedHashMap) user.get("Collaborateur");
				int idCollaborateur = (int) collaborateur.get("idCollaborateur");
				
				allBesoins.addAll(repository.getAllBesoinsByCollaborateur(idCollaborateur));
				allBesoinsPlanifier.addAll(repository.getBesoinPlanifierByCollaborateur(idCollaborateur));
				allBesoinsNonPlanifier.addAll(repository.getBesoinNonPlanifierByCollaborateur(idCollaborateur));
			}
			
			allBesoins.addAll(repository.getAllBesoinsByCollaborateur(tl.getIdTeamLead()));
			allBesoinsPlanifier.addAll(repository.getBesoinPlanifierByCollaborateur(tl.getIdTeamLead()));
			allBesoinsNonPlanifier.addAll(repository.getBesoinNonPlanifierByCollaborateur(tl.getIdTeamLead()));
			
		}
		
		List<Predicate<Besoins>> allPredicates = new ArrayList<Predicate<Besoins>>();
		if(bu != "") {
			Enum enumBU = Enum.valueOf(BU.class, bu);
			allPredicates.add(b -> b.getBu().equals(enumBU));
		}
		if(quarter != 0) {
			allPredicates.add(b -> b.getQuarter() == quarter);
		}
		if(annee != 0) {
			allPredicates.add(b -> b.getAnnee() == annee);
		}
		if(theme != "") {
			allPredicates.add(b -> b.getTheme().getNom().equals(theme));
		}
		
		List<Besoins> result = allBesoins.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
		List<Besoins> resultPlanifier = allBesoinsPlanifier.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
		List<Besoins> resultNonPlanifier = allBesoinsNonPlanifier.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
		
		Map<Object, List<Besoins>> mapp = result.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
		Map<Object, List<Besoins>> mapPlanifier = resultPlanifier.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
		Map<Object, List<Besoins>> mapNonPlanifier = resultNonPlanifier.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
		
		Map<Object,JSONObject> all = new HashMap<Object, JSONObject>();
		
		for(Map.Entry<Object, List<Besoins>> entry : mapp.entrySet()) {
			Object nomTheme = entry.getKey();			
			
			JSONObject json = new JSONObject();
			json.put("Results", entry.getValue());
			json.put("ResultsPlanifier", mapPlanifier.get(nomTheme));
			json.put("ResultsNonPlanifier", mapNonPlanifier.get(nomTheme));
			all.put(nomTheme, json);
		}
		
		jo.put("Results", all);
		return jo;
		
	}
	
	/*********************************** BESOINS PAR TYPE THEME ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject getBesoinsByThemeType(String type) {
		JSONObject jo = new JSONObject();
		
		if(repository.findAll().size() != 0 ) {
			
			List<Predicate<Besoins>> allPredicates = new ArrayList<Predicate<Besoins>>();
			if(type != "") {
				Enum enumType = Enum.valueOf(TypeTheme.class, type);
				allPredicates.add(b -> b.getTheme().getType().equals(enumType));
			}
			List<Besoins> result = repository.findAll().stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
			
			
			Map<Object, List<Besoins>> map = result.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
			
			Map<Object,JSONObject> all = new HashMap<Object, JSONObject>();
			for(Map.Entry<Object, List<Besoins>> entry : map.entrySet()) {
				Object nomTheme = entry.getKey();				
				JSONObject json = new JSONObject();
				json.put("All", entry.getValue());
				Random obj = new Random();
				int rand_num = obj.nextInt(0xffffff + 1);
				String colorCode = String.format("#%06x", rand_num);
				json.put("Color", colorCode);
				all.put(nomTheme,json);
			}

			jo.put("Results", all);
			return jo;
			
		}else {
			jo.put("Error", "Type n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** BESOINS PAR PROJET ***************************************/
	public JSONObject getBesoinsByProjet(String projet) {
		JSONObject jo = new JSONObject();
		
		if(repository.findAll().size() != 0 ) {
			
			List<Predicate<Besoins>> allPredicates = new ArrayList<Predicate<Besoins>>();
			if(projet != "") {
				allPredicates.add(b -> b.getProjet().getNom().equals(projet));
			}
			List<Besoins> result = repository.findAll().stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
			
			
			Map<Object, List<Besoins>> map = result.stream().collect(Collectors.groupingBy(b->b.getTheme().getNom()));
			
			Map<Object,JSONObject> all = new HashMap<Object, JSONObject>();
			for(Map.Entry<Object, List<Besoins>> entry : map.entrySet()) {
				Object nomTheme = entry.getKey();				
				JSONObject json = new JSONObject();
				json.put("All", entry.getValue());
				Random obj = new Random();
				int rand_num = obj.nextInt(0xffffff + 1);
				String colorCode = String.format("#%06x", rand_num);
				json.put("Color", colorCode);
				all.put(nomTheme,json);
			}

			jo.put("Results", all);
			return jo;
			
		}else {
			jo.put("Error", "Projet n'existe pas !");
			return jo;
		}
	}
	
	
	
	
}
