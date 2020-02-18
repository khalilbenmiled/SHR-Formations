package com.soprahr.API;

import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soprahr.Services.ReservationServices;
import com.soprahr.Services.SalleServices;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping( value = "/reservations" )
public class ReservationAPI {
	
	@Autowired
	public SalleServices serviceS;
	
	@Autowired
	public ReservationServices serviceR;

	@PostMapping(value ="",produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject reserverSalle(@Param(value = "idSalle") int idSalle , @Param( value ="idProgramme") int idProgramme) {
		return serviceS.reserverSalle(idSalle, idProgramme);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllReservations() {
		return serviceR.getAllReservations();
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteReservation(@Param(value = "id") int id) {
		return serviceR.deleteReservation(id);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getReservationById(@PathParam(value = "id") int id) {
		return serviceR.getReservationById(id);
	}
	

}
