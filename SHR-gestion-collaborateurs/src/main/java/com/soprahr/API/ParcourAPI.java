package com.soprahr.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprahr.Services.ParcourService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/parcours")
public class ParcourAPI {

	@Autowired
	public ParcourService service;
	
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject addParcour(@Param(value = "idCollaborateur") int idCollaborateur , @Param(value = "idFormation") int idFormation ) {
		return service.addParcour(idCollaborateur, idFormation );
	}
	
	@PostMapping(value = "/byCollaborateur" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getParcour(@Param(value = "idCollaborateur") int idCollaborateur ) {
		return service.getParcour(idCollaborateur);
	}
}
