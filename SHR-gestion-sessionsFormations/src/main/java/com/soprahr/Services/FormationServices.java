package com.soprahr.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.soprahr.RabbitMQ.RabbitMQSender;
import com.soprahr.Repository.FormationRepository;
import com.soprahr.Repository.ModuleRepository;
import com.soprahr.Repository.SessionRepository;
import com.soprahr.Repository.ThemeRepository;
import com.soprahr.models.Formation;
import com.soprahr.models.ModulesFormation;
import com.soprahr.models.Participants;
import com.soprahr.models.Session;
	

import net.minidev.json.JSONObject;


@Service
public class FormationServices {

	@Autowired
	public FormationRepository repository;
	@Autowired
	public SessionRepository repositoryS;
	@Autowired
	public ModuleRepository repositoryM;
	@Autowired
	public ThemeRepository repositoryT;
	@Autowired
	public RabbitMQSender rabbitMQSender;
	
	
	/*********************************** AJOUTER UNE FORMATION ***************************************/
	public JSONObject addFormation(Formation formation) {
		JSONObject jo = new JSONObject();
		Formation f = repository.save(formation);
		jo.put("Formation",f);
		/*------------------ DECLENCHER UN EVENEMENT AUX AUTRES SERICES --------------------------------*/
		JSONObject obj = new JSONObject();
		obj.put("formation", f.getId());
		rabbitMQSender.send(obj);
		/*----------------------------------------------------------------------------------------------*/
		return jo;
	}
	
	/*********************************** LISTE FORMATIONS ***************************************/
	public JSONObject getAllFormations() {
		JSONObject jo = new JSONObject();
		if ( repository.findAll().size() != 0 ) {
			jo.put("Formations" , repository.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des formations est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UNE FORMATION ***************************************/
	public JSONObject deleteFormation(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			repository.delete(repository.findById(id).get());
			jo.put("Success", "Formation supprimé");
			return jo;
		}else {
			jo.put("Error" , "Formation n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UNE FORMATION PAR ID ***************************************/
	public JSONObject getFormationById(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			jo.put("Formation", repository.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Formation n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** AJOUTER UNE FORMATION PAR PARAM ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject ajouterFormation(String nomTheme , String typeTheme ,String dateDebutStr, String dateFinStr , int maxParticipants, int duree,int idSession,int quarter, ArrayList modules , ArrayList arrayParticipants) {
		JSONObject jo = new JSONObject();
		Formation f = new Formation();
		try {
			
			
			if (repositoryS.findById(idSession).isPresent()) {
				Date dateDebut=new SimpleDateFormat("dd/MM/yy HH:mm" ).parse(dateDebutStr);
				Date dateFin=new SimpleDateFormat("dd/MM/yy HH:mm" ).parse(dateFinStr);
				
				List<ModulesFormation> listModulesFormation = new ArrayList<ModulesFormation>();
				for(Object object : modules) {
					LinkedHashMap obj = (LinkedHashMap) object;
					String nomModule = (String) obj.get("nom");
					String descriptionModule = (String) obj.get("description");
					ModulesFormation moduleFormation = new ModulesFormation();
					moduleFormation.setNom(nomModule);
					moduleFormation.setDescription(descriptionModule);
					listModulesFormation.add(moduleFormation);
				}
				
				List<Participants> listParticipants = new ArrayList<Participants>();
				for(Object object : arrayParticipants) {
					LinkedHashMap obj = (LinkedHashMap) object;
					int idParticipant = (int) obj.get("id");
					Participants participant = new Participants();
					participant.setIdParticipant(idParticipant);
					listParticipants.add(participant);	
				}
				
				f.setListModules(listModulesFormation);
				f.setListParticipants(listParticipants);
				f.setNomTheme(nomTheme);
				f.setTypeTheme(typeTheme);
				f.setDateDebut(dateDebut);
				f.setDateFin(dateFin);
				f.setMaxParticipants(maxParticipants);
				f.setDuree(duree);
				Session session = repositoryS.findById(idSession).get();
				List<Formation> listFormation = session.getListFormations();
				Formation newFormation = repository.save(f);
				listFormation.add(newFormation);
				session.setListFormations(listFormation);
				session.setTrimestre(quarter);
				repositoryS.save(session);
				
				jo.put("Formation", newFormation);
				return jo;
			}else {
				jo.put("Error", "Session n'existe pas !");
				return jo;
			}
			
			
		} catch (ParseException e) {
			e.printStackTrace();
			jo.put("Error", e);
			return jo;
		}
		
	}
	
	/*********************************** AFFICHER LA LISTE DES PARTICIPANTS D'UNE FORMATION ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject gettListParticipants(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			List<Object> tabs = new ArrayList<Object>();
			List<Participants> list = repository.findById(id).get().getListParticipants();
			for(Participants participant : list) {
				ResponseEntity<JSONObject> user = getUserAPI("http://localhost:8181/users/byId", participant.getIdParticipant());
				if(user.getBody().containsKey("Error")) {
					jo.put("Error" , user.getBody().get("Error"));
					return jo;
				}else {
					LinkedHashMap partBody = (LinkedHashMap) user.getBody().get("User");
					tabs.add(partBody);
				}
			}
			jo.put("Participants",tabs);
			return jo;
		}else {
			jo.put("Error" , "Formation n'existe pas !");
			return jo;
		}
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
	
	
	
	
	
	
	
}
