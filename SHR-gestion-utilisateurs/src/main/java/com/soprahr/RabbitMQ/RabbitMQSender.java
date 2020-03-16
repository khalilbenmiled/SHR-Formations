package com.soprahr.RabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;



@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	

	private String exchange = "exchange";
	

	private String routingkey = "routingKey";
	
	
	public void send(JSONObject obj) {
		amqpTemplate.convertAndSend(exchange, routingkey, obj);
	    
	}
}