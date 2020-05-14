package com.soprahr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.BesoinsRepository;

import net.minidev.json.JSONObject;

@Service
public class ReportingBesoinsService {

	@Autowired
	public BesoinsRepository repository;
	
	/*********************************** FORMATION DEMANDER PAR BU ***************************************/
	public JSONObject getFormationDemanderParBU(String bu) {
		JSONObject jo = new JSONObject();
		if(repository.getFormationDemanderParBU(bu).size() != 0) {
			jo.put("Besoins", repository.getFormationDemanderParBU(bu) );
			return jo;
		}else {
			jo.put("Error", "La list est vide ");
			return jo;
		}
	}
}
