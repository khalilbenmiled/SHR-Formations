package com.soprahr.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.BesoinsRepository;
import com.soprahr.model.BU;
import com.soprahr.model.Besoins;
import com.soprahr.model.TypeTheme;

import net.minidev.json.JSONObject;

@Service
public class ReportingBesoinsService {

	@Autowired
	public BesoinsRepository repository;
		
	/*********************************** BESOINS DEMANDER PAR FILTER ***************************************/
	@SuppressWarnings({ "rawtypes" })
	public JSONObject filterFormationDemander(String bu , int quarter , String theme) {
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
