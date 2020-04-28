package com.soprahr.API;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soprahr.Services.CabinetServices;
import com.soprahr.models.Cabinet;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "cabinets")
public class CabinetAPI {
	
	@Autowired
	public CabinetServices service;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addCabinet(@RequestBody Cabinet cabinet) {
		return service.addCabinet(cabinet);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllCabinets() {
		return service.getAllCabinets();
	}
	
	@PostMapping(value="delete" , produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject deleteCabinet(@Param(value = "id") int id) {
		return service.deleteCabinet(id);
	}
	
	@PostMapping(value="/cabinetOrFormateur" , produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getCabinetOrFormateur(@Param(value = "id") int id) {
		return service.getCabinetOrFormateur(id);
	}
	
	@GetMapping(value ="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getCabinetById(@PathParam(value = "id") int id) {
		return service.getCabinetById(id);
	}
	
	@PostMapping(value = "/addFAC" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addFormateurCabinet(@Param(value = "idFormateur") int idFormateur , @Param(value = "idCabinet") int idCabinet) {
		return service.addFormateurCabinet(idFormateur, idCabinet);
	}
	
	@PostMapping(value = "/addDAC" ,  produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addDomaineCabinet(@Param (value = "idDomaine") int idDomaine , @Param (value ="idCabinet") int idCabinet) {
		return service.addDomaineCabinet(idDomaine, idCabinet);
	}

}
