package com.soprahr.RabbitMQ;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soprahr.Services.CollaborateurServices;
import com.soprahr.models.Collaborateur;

import net.minidev.json.JSONObject;


@Component
public class RabbitMQListener {
	
	@Autowired
	public CollaborateurServices service;
	
	
	@RabbitListener(queues = "MyQueue")
	public void recievedMessageFromQueue(JSONObject obj) {
		if(obj.containsKey("Collaborateur")) {
			int id = (int) obj.getAsNumber("Collaborateur");
			Collaborateur collaborateur = new Collaborateur();
			collaborateur.setIdCollaborateur(id);
			service.addCollaborateur(collaborateur);
		}	
	}
}
