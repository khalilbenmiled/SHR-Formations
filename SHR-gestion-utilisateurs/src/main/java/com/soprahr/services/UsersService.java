package com.soprahr.services;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.soprahr.RabbitMQ.RabbitMQSender;
import com.soprahr.models.BU;
import com.soprahr.models.Role;
import com.soprahr.models.User;
import com.soprahr.repository.UsersRepository;
import com.soprahr.utils.PROXY;
import com.soprahr.utils.Utils;

import net.minidev.json.JSONObject;

@Service
public class UsersService {

	@Autowired
	public UsersRepository repository;
	@Autowired
	public RabbitMQSender rabbitMQSender;
	@Autowired
	public JavaMailSender emailSender;
	
	
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
	
	/*********************************** LISTE TEAMLEAD ***************************************/
	public JSONObject getAllTeamLead() {
		JSONObject jo = new JSONObject();
		if ( repository.findAllTeamlLead().size() != 0 ) {
			jo.put("Users" , repository.findAllTeamlLead());
			return jo;
		}else {
			jo.put("Error" , "La liste des users est vide");
			return jo;
		}
	}
	
	
	/*********************************** LISTE MANAGER ***************************************/
	public JSONObject findAllManager() {
		JSONObject jo = new JSONObject();
		if ( repository.findAllManager().size() != 0 ) {
			jo.put("Users" , repository.findAllManager());
			return jo;
		}else {
			jo.put("Error" , "La liste des users est vide");
			return jo;
		}
	}
	
	/*********************************** DESACTIVER UN USER ***************************************/
	public JSONObject deleteUser(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			User user = repository.findById(id).get();
			user.setDeleted(true);
			
			jo.put("User", repository.save(user));
			return jo;
		}else {
			jo.put("Error" , "User n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** ACTIVER UN USER ***************************************/
	public JSONObject activateUser(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			User user = repository.findById(id).get();
			user.setDeleted(false);
			
			jo.put("User",repository.save(user));
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
			
			
			try {
				String passswordHashed = Utils.toMD5(password);
				user.setPassword(passswordHashed);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			user.setPasswordChanged(false);
			
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo("khalilbenmiled93@gmail.com");
		    message.setSubject("Inscription SHR-Formation");
		    message.setText("Bonjour, vous etes inscrit sur la plateform SHR-Formation. Votre mot de passe est :"+password);
		    this.emailSender.send(message);
		    
			jo.put("Collaborateur", repository.save(user));
			return jo;
		}else {
			jo.put("Error", "Collaborateur existe deja !");
			return jo;
		}
	}
	
	/*********************************** INSCRIRE FROM FILE COLLABORATEUR ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject registerFromFile(ArrayList users) {
//		JSONObject jo = new JSONObject();
		
		
	
		try {
			Properties properties = System.getProperties();
		    properties.put("mail.smtp.host", "smtp.gmail.com");
		    properties.put("mail.smtp.port", "" + 587);
		    properties.put("mail.smtp.starttls.enable", "true");
		    Session session = Session.getInstance(properties);
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", "khalil.benmiled@esprit.tn", "05494282");
			
			for(Object object : users) {
				LinkedHashMap obj = (LinkedHashMap) object;
				String nom = (String) obj.get("Nom");
				String prenom = (String) obj.get("Prenom");	
				String email = (String) obj.get("Email");
				String role = (String) obj.get("Role");
				String bu = (String) obj.get("Bu");
				
				
				User user = new User();
				if(repository.getUserByEmail(email) == null) {
					Utils utils = new Utils();
					String password = utils.generatePassword();
					user.setNom(nom);
					user.setPrenom(prenom);
					user.setEmail(email);
					user.setRole(Role.valueOf(role));
					user.setBu(BU.valueOf(bu));

					String passswordHashed = Utils.toMD5(password);
					user.setPassword(passswordHashed);
					user.setPasswordChanged(false);
					
					repository.save(user);
					
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("khalil.benmiled@esprit.tn"));
					InternetAddress[] address = {new InternetAddress("khalilbenmiled93@gmail.com")};
					message.setRecipients(Message.RecipientType.TO, address);
					message.setSubject("SHR-Formation convocation"); 
					message.setSentDate(new Date());
					
					
					message.setText("Bonjour, vous etes inscrit sur la plateform SHR-Formation. Votre mot de passe est :"+password);                  
					message.saveChanges();
				    transport.sendMessage(message, address);
					
				  
				}
			}
			return null;
		} catch (MessagingException | NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		return null;
	}
	

	
	/*********************************** UPDATE PASSWORD ***************************************/
	public JSONObject updatePassword(int id , String oldPassword,String newPassword) {
		JSONObject jo = new JSONObject();
		if (repository.findById(id).isPresent()) {
			User user = repository.findById(id).get();
			try {
				String hashOldPassword = Utils.toMD5(oldPassword);
				if(!user.getPassword().equals(hashOldPassword)) {
					jo.put("Error" , "Password incorrecte ! ");
					return jo;
				}
			
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
	
	/*********************************** GET INFOS COLLABORATEURS ***************************************/
	public JSONObject getInfosCollaborateur(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			if(repository.findById(id).get().getRole() == Role.COLLABORATEUR) {
				ResponseEntity<JSONObject> response  = getUserAPI(PROXY.Collaborateurs+"/collaborateurs/getTLCollaborateur" , id);
				
				if(response.getBody().containsKey("Error")) {
					jo.put("Error" , response.getBody().get("Error"));
					return jo;
				}else {
					int idTeamLead = (int) response.getBody().get("idTL");
					if(repository.findById(idTeamLead).isPresent()) {
						
						User teamlead = repository.findById(idTeamLead).get();
						ResponseEntity<JSONObject> response2  = getUserAPI(PROXY.Besoins+"/teamlead/getManagerTL" , teamlead.getId());
						
						if(response2.getBody().containsKey("Error")) {
							jo.put("Error" , response2.getBody().get("Error"));
							return jo;
						}else {
							int idManager = (int) response2.getBody().get("idMG");
							if(repository.findById(idManager).isPresent()) {
								User manager = repository.findById(idManager).get();
								jo.put("TeamLead",teamlead);
								jo.put("Manager", manager);
								return jo;
							}else {
								jo.put("TeamLead",teamlead);
								jo.put("Manager", null);
								return jo;
							}
						}
						
						
					}else {
						jo.put("Error", "Team Lead n'existe pas !");
						return jo;
					}
				}
			}
			return null;
		}else {
			jo.put("Error", "Collaborateur n'existe pas !");
			return jo;
		}

	}
	
	/*********************************** GET INFOS COLLABORATEURS ***************************************/
	public JSONObject getInfosTL (int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			User teamlead = repository.findById(id).get();
			ResponseEntity<JSONObject> response  = getUserAPI(PROXY.Besoins+"/teamlead/getManagerTL" , teamlead.getId());
			
			if(response.getBody().containsKey("Error")) {
				jo.put("Error" , response.getBody().get("Error"));
				return jo;
			}else {
				int idManager = (int) response.getBody().get("idMG");
				if(repository.findById(idManager).isPresent()) {
					User manager = repository.findById(idManager).get();
					jo.put("Manager", manager);
					return jo;
				}else {
					jo.put("Error", "TeamLead n'existe pas !");
					return jo;
				}
			}
		}else {
			jo.put("Error", "TeamLead n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** GET FREE TEAMLEAD ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject getFreeTL () {
		JSONObject jo = new JSONObject();
		List<User> listFreeTL = new ArrayList<User>();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JSONObject> response = restTemplate.getForEntity(PROXY.Besoins+"/teamlead/getFreeTL", JSONObject.class );
		if(response.getBody().containsKey("Error")) {
			jo.put("Error" , response.getBody().get("Error"));
			return jo;
		}else {
			ArrayList listTeamLead = (ArrayList) response.getBody().get("TeamLeads");
			for (Object obj : listTeamLead) {
				LinkedHashMap teamlead = (LinkedHashMap) obj;
				int id = (int) teamlead.get("idTeamLead");
				listFreeTL.add(repository.findById(id).get());
			}
			jo.put("TeamLeads" , listFreeTL);
			return jo;
		}
	}
	
	
	/*********************************** API USER BY EMAIL ***************************************/
	
	public ResponseEntity<JSONObject> getUserAPIByEmail(String uri , String email) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("id", email);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<JSONObject> response = restTemplate.postForEntity( uri, request , JSONObject.class );
		return response;
	}
	
	/*********************************** API USER BY ID ***************************************/
	public ResponseEntity<JSONObject> getUserAPI(String uri , int id) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
		map.add("id", id);
		HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
		ResponseEntity<JSONObject> response = restTemplate.postForEntity( uri, request , JSONObject.class );
		return response;
	}
	

	
	
//	/*********************************** ENVOYER DOCUMENT AU COLLABORATEUR ***************************************/
//	public JSONObject sendConvocation() {
//		
//		try {
//			MimeMessage message = emailSender.createMimeMessage();
//			boolean multipart = true;
//			MimeMessageHelper helper = new MimeMessageHelper(message, multipart);
//			helper.setTo("khalilbenmiled93@gmail.com");
//			helper.setSubject("Test email with attachments");
//			helper.setText("Hello, Im testing email with attachments!");
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	
	
	
	
	
	
}
