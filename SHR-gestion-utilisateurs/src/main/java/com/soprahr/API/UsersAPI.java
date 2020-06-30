package com.soprahr.API;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soprahr.models.User;
import com.soprahr.repository.UsersRepository;
import com.soprahr.services.UsersService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/users")
public class UsersAPI {

	@Autowired
	public UsersService service;
	@Autowired
	public UsersRepository repository;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject addUser(@RequestBody User user) {
		return service.addCollaborateur(user);
	}
	
	@PostMapping(value="/updatePassword" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject updatePassword(@Param(value ="id") int id , @Param(value = "oldPassword") String oldPassword,@Param(value = "newPassword") String newPassword) {
		return service.updatePassword(id, oldPassword,newPassword);
	}
	
	@PostMapping(value="/modifier" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject modifierUtilisateur(@RequestBody JSONObject user) {
		
		return service.modifierUtilisateur(user);
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllUsers() {
		return service.getAllUsers();
	}
	
	@GetMapping(value="/tl" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllTeamLead() {
		return service.getAllTeamLead();
	}
	
	@GetMapping(value="/mg" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject findAllManager() {
		return service.findAllManager();
	}
	
	

	@DeleteMapping(value="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteUser(@PathVariable(value = "id") int id) {
		return service.deleteUser(id);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getUserById(@PathParam(value = "id") int id) {
		return service.getUserById(id);
	}

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject logIn(@Param(value = "email") String email, @Param(value = "password") String password) {
		return service.logIn(email, password);				
	}
	
	@GetMapping(value = "/collaborateurs", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject allCollaborateurs() {
		return service.allCollaborateurs();			
	}
	
	@PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject logOut(@Param(value = "id") int id) {
		return service.logOut(id);			
	}
	
	@PostMapping(value = "/byId", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getUserByID(@Param(value = "id") int id) {
		JSONObject jo = new JSONObject();	
		if(repository.findById(id).isPresent()) {
			jo.put("User" , repository.findById(id).get());
			return jo;
		}else {
			jo.put("Error", "User n'existe pas !");
			return jo;
		}
	}
	
	@PostMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getRoleUser(@Param(value = "id") int id) {
		JSONObject jo = new JSONObject();	
		if(repository.findById(id).isPresent()) {
			jo.put("Role" , repository.findById(id).get().getRole());
			return jo;
		}else {
			jo.put("Error", "User n'existe pas !");
			return jo;
		}
	}
	
	@PostMapping(value = "/getInfosCollaborateur", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getInfosCollaborateur(@Param(value = "id") int id) {
		return service.getInfosCollaborateur(id);
	}
	
	@PostMapping(value = "/getInfosTL", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getInfosTL(@Param(value = "id") int id) {
		return service.getInfosTL(id);
	}
	
	@GetMapping(value = "/getFreeTL", produces = MediaType.APPLICATION_JSON_VALUE )
	public JSONObject getFreeTL() {
		return service.getFreeTL();
	}
	
	@PostMapping(value = "/activate", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject activateUser(@Param(value = "id") int id) {
		return service.activateUser(id);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/registerFromFile", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject registerFromFile(@RequestBody JSONObject users) {
	
		ArrayList arrayUsers = (ArrayList) users.get("users");
		return service.registerFromFile(arrayUsers);
	}
	
	@PostMapping(value = "/sendEmail", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject registerFromFile(@RequestBody List<User> users) {
		return service.sendMail(users);
	}
	
	
	

	

	

}
