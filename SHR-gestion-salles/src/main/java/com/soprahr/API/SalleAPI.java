package com.soprahr.API;


import javax.websocket.server.PathParam;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprahr.RabbitMQ.RabbitMQSender;
import com.soprahr.Services.SalleServices;
import com.soprahr.models.Salle;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/salles")
public class SalleAPI {

	@Autowired
	public SalleServices service;
	@Autowired
	public RabbitMQSender rabbitMQSender;
	
	@RabbitListener(queues = "MyQueue")
	public void recievedMessage(String msg) {
		System.out.println("Recieved Message From RabbitMQ: " + msg);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addSalle(@RequestBody Salle salle) {
		return service.addSalle(salle);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllSalles() {
		return service.getAllSalles();
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteSalle(@Param(value = "id") int id) {
		return service.deleteSalle(id);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getSalleById(@PathParam(value = "id") int id) {
		return service.getSalleById(id);
	}
	
	@PostMapping(value = "/addPAS" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addProgrammeSalle(@Param(value = "idProgramme") int idProgramme , @Param(value = "idSalle") int idSalle) {
		return service.addProgrammeSalle(idProgramme, idSalle);
	}
	
	@GetMapping(value = "/verifDispoSalle" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject verifDispoSalle(@Param(value = "idSalle") int idSalle,@Param (value = "date1") String date) {
		return service.verifDispoSalle(idSalle,date);
	}
	
	@PostMapping(value = "/send")
	public void Test() {
		rabbitMQSender.send();
	}
}
