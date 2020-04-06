package com.soprahr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.TeamLeadRepository;

import net.minidev.json.JSONObject;

@Service
public class TeamLeadService {

	@Autowired
	public TeamLeadRepository repository;
	
	/*********************************** AFFICHER TEAMLEAD PAR ID ***************************************/
	public JSONObject getTeamLeadById(int id) {
		JSONObject jo = new JSONObject();
		if(repository.getTeamLeadById(id) != null) {
			jo.put("TeamLead", repository.getTeamLeadById(id));
			return jo;
		}else {
			jo.put("Error", "TeamLead n'existe pas !");
			return jo;
		}
	}
}
