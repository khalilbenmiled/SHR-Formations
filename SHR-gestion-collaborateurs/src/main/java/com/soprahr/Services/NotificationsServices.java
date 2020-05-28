package com.soprahr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.NotificationsRepository;
import com.soprahr.models.MyNotification;

import net.minidev.json.JSONObject;

@Service
public class NotificationsServices {

	@Autowired
	public NotificationsRepository repository;
	
	public JSONObject save(MyNotification notification) {
		JSONObject jo = new JSONObject();
		jo.put("Notifications", repository.save(notification));
		return jo;
	}
	
	public JSONObject getAll() {
		JSONObject jo = new JSONObject();
		if(repository.findAll().size() != 0) {
			jo.put("Notifications", repository.findAll());
			return jo;
		}else {
			jo.put("Error", "La list est vide !");
			return jo;
		}
	
	}
	
	public JSONObject updateMesNotifications(int idCollaborateur) {
		JSONObject jo = new JSONObject();
		if(repository.getMesNotifications(idCollaborateur).size() != 0) {
			for(MyNotification notification : repository.getMesNotifications(idCollaborateur)) {
				notification.setOpened(true);
				repository.save(notification);
			}
			jo.put("Notifications", repository.getMesNotifications(idCollaborateur));
			return jo;
		}else {
			jo.put("Error", "La list est vide");
			return jo;
		}

	}
	
	public JSONObject deleteAllNotifications(int id) {
		JSONObject jo = new JSONObject();
		if(repository.getMesNotifications(id).size() != 0) {
			for(MyNotification notification : repository.getMesNotifications(id)) {
				repository.delete(notification);
			}
			jo.put("Success" , "Notifications supprimées ");
			return jo;
		}else {
			jo.put("Error", "La list est vide");
			return jo;
		}
	}
}
