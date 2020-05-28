package com.soprahr.notification;

import org.springframework.stereotype.Controller;

import net.minidev.json.JSONObject;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@Controller
public class GreetingController {

	  @MessageMapping("/hello")
	  @SendTo("/topic/greetings")
	  public String greeting() throws Exception {
	    Thread.sleep(1000); // simulated delay
	    return "Hello khalil ";
	  }
	  
		@MessageMapping("/valider")
		@SendTo("/topic/validerByTL")
		public JSONObject besoinValider(JSONObject jo) {	
			
			return jo;
		}
}
