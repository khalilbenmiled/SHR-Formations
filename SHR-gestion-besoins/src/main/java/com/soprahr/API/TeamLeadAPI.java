package com.soprahr.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprahr.Services.TeamLeadService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/teamlead")
public class TeamLeadAPI {

	@Autowired
	public TeamLeadService service;
	
	
	@PostMapping(value = "/byID", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getTeamLeadById(@Param(value = "id") int id ) {
		return service.getTeamLeadById(id);
	}
}
