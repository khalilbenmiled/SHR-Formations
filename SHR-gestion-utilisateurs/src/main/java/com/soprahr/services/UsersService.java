package com.soprahr.services;

import java.security.NoSuchAlgorithmException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.RabbitMQ.RabbitMQSender;
import com.soprahr.models.Role;
import com.soprahr.models.User;
import com.soprahr.repository.UsersRepository;
import com.soprahr.utils.Utils;

import net.minidev.json.JSONObject;

@Service
public class UsersService {

	@Autowired
	public UsersRepository repository;
	@Autowired
	public RabbitMQSender rabbitMQSender;
	
	
	/*********************************** AJOUTER UN USER ***************************************/
	public JSONObject addUser(User user) {
		JSONObject jo = new JSONObject();
		JSONObject obj = new JSONObject();
		
		User u = repository.save(user);
				
		if(u.getRole().equals(Role.COLLABORATEUR)) {
			obj.put("Collaborateur", u.getId());
			rabbitMQSender.send(obj);
		}else if(u.getRole().equals(Role.TEAMLEAD)) {
			obj.put("TeamLead", u.getId());
			rabbitMQSender.send(obj);
		}else if(u.getRole().equals(Role.MANAGER)) {
			obj.put("Manager", u.getId());
			rabbitMQSender.send(obj);
		}else if (u.getRole().equals(Role.SERVICEFORMATIONS)) {
			obj.put("ServiceFormations", u.getId());
			rabbitMQSender.send(obj);
		}
		
		jo.put("User",u);
		return jo;
	}
	
	/*********************************** LISTE USERS ***************************************/
	public JSONObject getAllUsers() {
		JSONObject jo = new JSONObject();
		if ( repository.findAll().size() != 0 ) {
			jo.put("Users" , repository.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des users est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UN USER ***************************************/
	public JSONObject deleteUser(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			repository.delete(repository.findById(id).get());
			jo.put("Success", "User supprimé");
			return jo;
		}else {
			jo.put("Error" , "User n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UN USER PAR ID ***************************************/
	public JSONObject getUserById(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			jo.put("User", repository.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "User n'existe pas !");
			return jo;
		}
	}

	/*********************************** INSCRIRE COLLABORATEUR ***************************************/
	public JSONObject addCollaborateur(User user) {
		JSONObject jo = new JSONObject();
		if(repository.getUserByEmail(user.getEmail()) == null) {
			Utils utils = new Utils();
			String password = utils.generatePassword();
			user.setPassword(password);
			user.setPasswordChanged(false);
			repository.save(user);
			jo.put("Collaborateur", user);
			return jo;
		}else {
			jo.put("Error", "Collaborateur existe deja !");
			return jo;
		}
	}
	
	/*********************************** UPDATE PASSWORD ***************************************/
	public JSONObject updatePassword(int id , String oldPassword,String newPassword) {
		JSONObject jo = new JSONObject();
		if (repository.findById(id).isPresent()) {
			User user = repository.findById(id).get();
			if(!user.getPassword().equals(oldPassword)) {
				jo.put("Error" , "Password incorrecte ! ");
				return jo;
			}
			try {
				String passswordHashed = Utils.toMD5(newPassword);
				user.setPassword(passswordHashed);
				user.setPasswordChanged(true);
				jo.put("Collaborateur", repository.save(user));
				return jo;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				jo.put("Error", e);
				return jo;		
			}
		
		}else {
			jo.put("Error", "Collaborateur n'existe pas ! ");
			return jo;
		}	
	}
	
	/*********************************** LOGIN ***************************************/
	public JSONObject logIn(String email, String password) {
			JSONObject jo = new JSONObject();
			User user = repository.getUserByEmail(email);
			if(user == null) {
				jo.put("Error", "User's email n'existe pas !");
				return jo;
			}else {
				if(user.getPassword().equals(password)) {
					user.setConnected(true);
					jo.put("Success", repository.save(user));
					return jo;
				}
				try {
					String passwordHashed = Utils.toMD5(password);
					if(user.getPassword().equals(passwordHashed)) {
						user.setConnected(true);
						jo.put("Success", repository.save(user));
						return jo;
					}else {
						jo.put("Error", "Password inccorecte !");
						return jo;
					}
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					jo.put("Error", e);
					return jo;
				}
				
			}			
	}
	
	/*********************************** LOGOUT ***************************************/
	public JSONObject logOut(int id) {
		JSONObject jo = new JSONObject();
		User user = repository.findById(id).get();
		user.setConnected(false);
		jo.put("Success" , repository.save(user));
		
		return jo;
	}
	
	/*********************************** GET ALL COLLABORATEURS ***************************************/
	public JSONObject allCollaborateurs() {
		JSONObject jo = new JSONObject();
		
		if (repository.getAllCollaborateur().size() != 0) {
			jo.put("Collaborateurs", repository.getAllCollaborateur());
			return jo;
		}else {
			jo.put("Error", "La liste est vide");
			return jo;
		}
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
}
