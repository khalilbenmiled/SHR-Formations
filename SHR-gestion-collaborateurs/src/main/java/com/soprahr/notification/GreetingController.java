package com.soprahr.notification;

import org.springframework.stereotype.Controller;

import com.soprahr.Repository.NotificationsRepository;
import com.soprahr.models.MyNotification;

import net.minidev.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@Controller
public class GreetingController {

	@Autowired
	public NotificationsRepository repository;

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public String greeting() throws Exception {
		Thread.sleep(1000); // simulated delay
		return "Hello khalil ";
	}

	@MessageMapping("/valider")
	@SendTo("/topic/validerByTL")
	public JSONObject besoinValider(JSONObject jo) {
		JSONObject joo = new JSONObject();
		try {
			Date dateDebut = new SimpleDateFormat("dd/MM/yy HH:mm").parse(jo.getAsString("date"));
			String message = jo.getAsString("message");
			String idCollaborateur = jo.getAsString("idCollaborateur");
			String opened = jo.getAsString("opened");
			MyNotification notification = new MyNotification(message, Integer.parseInt(idCollaborateur), dateDebut,
					Boolean.parseBoolean(opened));
			repository.save(notification);
			joo.put("Notifications", repository.findAll());
			return joo;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@MessageMapping("/formationPlanifier")
	@SendTo("/topic/formationPlanifier")
	public String formationPlanifier() {
		return "Formation planifier ";
	}
}
