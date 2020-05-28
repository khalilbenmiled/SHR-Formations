package com.soprahr.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprahr.Services.NotificationsServices;
import com.soprahr.models.MyNotification;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/notifications")
public class NotificationsAPI {

	@Autowired
	public NotificationsServices service;
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject save(@RequestBody MyNotification notification) {
		return service.save(notification);
	}
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAll() {
		return service.getAll();
	}
	
	@PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject updateMesNotifications(@RequestBody JSONObject jo) {
		return service.updateMesNotifications(Integer.parseInt(jo.getAsString("id")));
	}
	
	@PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteAllNotifications(@RequestBody JSONObject jo) {
		return service.deleteAllNotifications(Integer.parseInt(jo.getAsString("id")));
	}
}
