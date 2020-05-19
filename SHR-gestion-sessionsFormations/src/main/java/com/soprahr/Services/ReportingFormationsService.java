package com.soprahr.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.FormationRepository;
import com.soprahr.models.Formation;


import net.minidev.json.JSONObject;

@Service
public class ReportingFormationsService {

	@Autowired
	public FormationRepository repository;
	
	/*********************************** FORMATION PAR RATING ***************************************/
	public JSONObject getFormationRating (int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			Formation formation = repository.findById(id).get();
			
			jo.put("Formation", formation);
			return jo;
		}else {
			jo.put("Error", "Formation n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** FORMATION PAR ETAT  ***************************************/
	public JSONObject getFormationByEtat(String nomTheme , String dateDeb , String dateF,String type)  {
		JSONObject jo = new JSONObject();
		List<Predicate<Formation>> allPredicates = new ArrayList<Predicate<Formation>>();
		List<Formation> listFormation = repository.findAll();
		
		if(nomTheme != "") {
			allPredicates.add(f->f.getNomTheme().equals(nomTheme));
		}
		
		if(type != "") {		
			allPredicates.add(f->f.getTypeTheme().equals(type));
		}
		
		if(dateDeb != "") {
			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				Date d1 = sdformat.parse(dateDeb);
				allPredicates.add(f->f.getDateDebut().compareTo(d1) > 0);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		
		if(dateF != "") {
			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				Date d1 = sdformat.parse(dateF);
				allPredicates.add(f->f.getDateFin().compareTo(d1) < 0);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		
				
		List<Formation> result = listFormation.stream().filter(allPredicates.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
		Map<Object, List<Formation>> map = result.stream().collect(Collectors.groupingBy(b->b.getNomTheme()));
		
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.add(Calendar.MONTH, 1);
		Map<Object,JSONObject> all = new HashMap<Object, JSONObject>();
		for(Map.Entry<Object, List<Formation>> entry : map.entrySet()) {
			Object theme = entry.getKey();	
			JSONObject json = new JSONObject();
			List<Formation> formationEnCours = new ArrayList<Formation>();
			List<Formation> formationProgrammé = new ArrayList<Formation>();
			List<Formation> formationTerminer = new ArrayList<Formation>();
			List<Formation> allFormations = new ArrayList<Formation>();
			
			for(Formation f : entry.getValue()) {
				
				Calendar dateDebut = Calendar.getInstance();
				dateDebut.setTime(f.getDateDebut());
				dateDebut.add(Calendar.MONTH, 1);
				Calendar dateFin = Calendar.getInstance();
				dateFin.setTime(f.getDateFin());
				dateFin.add(Calendar.MONTH, 1);

				if(dateDebut.compareTo(now) == -1 && dateFin.compareTo(now) == 1) {
					formationEnCours.add(f);
					allFormations.add(f);
				} else if( dateDebut.compareTo(now) == 1 && dateFin.compareTo(now) == 1 ) {
					formationProgrammé.add(f);
					allFormations.add(f);
				}else if (dateDebut.compareTo(now) == -1 && dateFin.compareTo(now) == -1) {
					allFormations.add(f);
					formationTerminer.add(f);
				}
			}
			json.put("All" , allFormations);
			json.put("EnCours" ,formationEnCours);
			json.put("Programmé" ,formationProgrammé );
			json.put("Terminer" ,formationTerminer );
			all.put(theme, json);
		}
		jo.put("Results", all);
		return jo;
	}
	
	
	
	
	
	
	
}
