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
import com.soprahr.Services.BesoinsService;
import com.soprahr.model.Besoins;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/besoins")
public class BesoinsAPI {

	@Autowired
	public BesoinsService service;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE )
	public JSONObject addBesoin(@RequestBody Besoins besoin) {
		return service.addBesoin(besoin);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllBesoins() {
		return service.getAllBesoins();
	}
	

	@PostMapping( value = "/remove" ,produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject deleteBesoin(@Param(value = "id") int id) {
		return service.deleteBesoin(id);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getBesoinById(@PathParam(value = "id") int id) {
		return service.getBesoinById(id);
	}
	
	@PostMapping(value = "/byTL", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getBesoinsByTL(@Param(value = "id") int id) {
		return service.getBesoinsByTL(id);
	}
	
	@PostMapping(value = "/byMG", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getBesoinsByMG(@Param(value = "id") int id) {
		return service.getBesoinsByManager(id);
	}

	@PostMapping(value = "/byUser", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getBesoinsByUser(@Param(value = "id") int id) {
		return service.getBesoinsByUser(id);
	}
	
	@PostMapping(value = "/valider", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject validerBesoinTL(@Param(value = "idBesoin") int idBesoin , @Param(value = "trimestre")int trimestre , @Param(value ="idProjet") int idProjet ,@Param(value ="idTL") int idTL ) {
		return service.validerBesoinTL(idBesoin, trimestre, idProjet , idTL);
	}
	
	@PostMapping(value = "/validerMG", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject validerBesoinMG(@Param(value = "idBesoin") int idBesoin ) {
		return service.validerBesoinMG(idBesoin);
	}
	
	@PostMapping(value = "/annulerValidationTL", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject annulerValidationBesoin(@Param(value = "idBesoin") int idBesoin ) {
		return service.annulerValidationBesoin(idBesoin);
	}
	
	@PostMapping(value = "/annulerValidationMG", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject annulerValidationBesoinMG(@Param(value = "idBesoin") int idBesoin ) {
		return service.annulerBesoinMG(idBesoin);
	}

	
}
