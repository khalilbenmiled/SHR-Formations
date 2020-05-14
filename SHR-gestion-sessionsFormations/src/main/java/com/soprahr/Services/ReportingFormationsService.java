package com.soprahr.Services;

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
}
