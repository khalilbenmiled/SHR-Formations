package com.soprahr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soprahr.models.User;
import com.soprahr.repository.UsersRepository;
import net.minidev.json.JSONObject;

@Service
public class UsersService {

	@Autowired
	public UsersRepository repository;

	public JSONObject logIn(String email, String password) {
		
		JSONObject jo = new JSONObject();
		User user = repository.getUserByEmail(email);
		if (user != null) {
			if (repository.verifyPassword(email , password) != null) {
				jo.put("user" , user);
				user.setConnected(true);
				repository.save(user);
				return jo;
			} else {
				jo.put("Error" , "User's password not valid");
				return jo;
			}
		} else {
			jo.put("Error" , "User's email not exist");
			return jo;
		}

	}
}
