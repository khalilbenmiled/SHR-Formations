package com.soprahr.API;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprahr.RabbitMQ.RabbitMQSender;
import com.soprahr.Repository.CollaborateurRepository;
import com.soprahr.models.Collaborateur;

@RestController
@RequestMapping(value = "/api")
public class CollaborateurAPI {
	
	
	@Autowired
	public CollaborateurRepository repository;
	@Autowired
	RabbitMQSender rabbitMQSender;
	
	@RabbitListener(queues = "EventFromSalle")
	public void recievedMessage(String msg) {
		System.out.println("Recieved Message From RabbitMQ: " + msg);
	}
	
	@GetMapping
	public List<Collaborateur> getAll(){
		return repository.findAll();
	}
	
	@PostMapping
	public void Test() {
		rabbitMQSender.send();
	}

}
