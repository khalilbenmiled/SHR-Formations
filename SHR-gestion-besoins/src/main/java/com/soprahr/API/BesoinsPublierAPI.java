package com.soprahr.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprahr.Services.BesoinPublierServices;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/besoinsPublier")
public class BesoinsPublierAPI {

	@Autowired
	public BesoinPublierServices service;
	
	@PostMapping(value = "/publier", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject addBesoinsPublier(@Param(value = "idBesoin") int idBesoin , @Param(value = "idManager") int idManager  ) {
		return service.addBesoinsPublier(idBesoin,idManager);
	}
	
	@PostMapping(value = "/publierBesoin", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject publierBesoin(@Param(value = "id") int id ) {
		return service.publierBesoin(id);
	}
	
	@PostMapping(value = "/retirer", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject retirerBesoinPublier(@Param(value = "idBesoin") int idBesoin , @Param(value = "idManager") int idManager) {
		return service.retirerBesoinPublier(idBesoin , idManager);
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject listBesoinsPublier(@Param(value = "id") int id) {
		return service.listBesoinsPublier(id);
	}
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject listBesoins() {
		return service.listBesoins();
	}
	
	@PostMapping(value = "/userInfos", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject userInfos(@Param(value = "id") int id ) {
		return service.getUserInfos(id);
	}
	
}
