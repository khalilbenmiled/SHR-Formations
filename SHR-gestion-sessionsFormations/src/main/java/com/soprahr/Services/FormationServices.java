package com.soprahr.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.soprahr.Repository.RatingRepository;
import com.soprahr.Repository.SessionRepository;
import com.soprahr.Repository.ThemeRepository;
import com.soprahr.models.Formation;
import com.soprahr.models.ModulesFormation;
import com.soprahr.models.Participants;
import com.soprahr.models.Rating;

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
	public RatingRepository repositoryR;
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
	
	/*********************************** SET LIST PARTICIPANT FORMATION ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject setListParticipantFormation(int id ,ArrayList arrayParticipants) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			Formation formation = repository.findById(id).get();
			List<Participants> listParticipants = new ArrayList<Participants>();
			
			for(Object object : arrayParticipants) {
				LinkedHashMap obj = (LinkedHashMap) object;
				int idParticipant = (int) obj.get("id");
				Participants participant = new Participants();
				participant.setIdParticipant(idParticipant);
				listParticipants.add(participant);	
			}
			List<Participants> allParticipants = formation.getListParticipants();
			allParticipants.addAll(listParticipants);
			formation.setListParticipants(allParticipants);
			repository.save(formation);
			jo.put("Formation" , repository.save(formation) );
			return jo;
			
		}else {
			jo.put("Error" , "Formation n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** AJOUTER UNE FORMATION PAR PARAM ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject ajouterFormation(
			String nomTheme , 
			String typeTheme ,
			String dateDebutStr, 
			String dateFinStr , 
			int maxParticipants, 
			int duree,
			int idSession,
			int quarter, 
			ArrayList modules , 
			ArrayList arrayParticipants,
			int idCF) {
		JSONObject jo = new JSONObject();
		Formation f = new Formation();
		try {
			
			
			
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
				f.setIdCF(idCF);
//				Session session = repositoryS.findById(idSession).get();
//				List<Formation> listFormation = session.getListFormations();
				Formation newFormation = repository.save(f);
//				listFormation.add(newFormation);
//				session.setListFormations(listFormation);
//				session.setTrimestre(quarter);
//				repositoryS.save(session);
				
				jo.put("Formation", newFormation);
				return jo;			
			
		} catch (ParseException e) {
			e.printStackTrace();
			jo.put("Error", e);
			return jo;
		}
		
	}
	
	/*********************************** AFFICHER LES FORMATION SAUF CETTE ID ***************************************/
	public JSONObject getFormationsWithouThistId(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			jo.put("Formations" , repository.getFormationsWithouThistId(id));
			return jo;
		}else {
			jo.put("Error", "Formation n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** AFFICHER LA LISTE DES COLLABORATEUR NON PARTICIPANT ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject getCollaborateurWithoutParticipants(int id) {
		JSONObject jo = new JSONObject();
		List<Object> list = new ArrayList<Object>();
		if(repository.findById(id).isPresent()) {
			ArrayList participants = (ArrayList) gettListParticipants(id).get("Participants");
			ResponseEntity<JSONObject> collaborateurResponse = getAllCollaborateur();
			ArrayList collaborateurs = (ArrayList) collaborateurResponse.getBody().get("Collaborateurs");
			for(Object objCol : collaborateurs) {
				LinkedHashMap collaborateur = (LinkedHashMap) objCol;
				int exist = 0;
				for (Object objPart : participants) {
					LinkedHashMap participant = (LinkedHashMap) objPart;
					if(collaborateur.get("id").equals(participant.get("id"))) {
						exist = 1;
					}
				}
				if(exist == 0) {
					JSONObject json = new JSONObject();
					json.put("data" , collaborateur);
					json.put("participe", "NON");
					list.add(json);
				}else {
					JSONObject json = new JSONObject();
					json.put("data" , collaborateur);
					json.put("participe", "OUI");
					list.add(json);
				}
			}
			
			jo.put("Collaborateurs" , list);
			return jo;
		}else {
			jo.put("Error", "Formation n'existe pas !");
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
	
	/*********************************** AFFICHER LES FORMATIONS PAR PARTICIPANT ***************************************/
	public JSONObject getFormationByParticipant(int idCollaborateur) {
		JSONObject jo = new JSONObject();
		List<Formation> listFormation = new ArrayList<Formation>();
		if(repository.findAll().size() != 0) {
			for(Formation formation : repository.findAll()) {
				Optional<Participants> part = formation.getListParticipants().stream().filter(p -> p.getIdParticipant() == idCollaborateur).findFirst();
				if(part.isPresent()) {
					listFormation.add(formation);
				}
			}
			jo.put("Formations", listFormation);
			return jo;
		}else {
			jo.put("Error" , "Formation n'existe pas !");
			return jo;
		}
	}
	
	
	/*********************************** RATE FORMATION ***************************************/
	public JSONObject rateFormation(int idFormation , int star) {
		JSONObject jo = new JSONObject();
		if(repository.findById(idFormation).isPresent()) {
			Formation formation = repository.findById(idFormation).get();
			Rating rating = formation.getRating();
			
			if(rating == null) {
				Rating newRating = new Rating();
				if(star == 1) {
					newRating.setStar1(1);
				}else if (star == 2) {
					newRating.setStar2(1);
				}else if (star == 3) {
					newRating.setStar3(1);
				}else if(star == 4) {
					newRating.setStar4(1);
				}else if (star == 5 ) {
					newRating.setStar5(1);
				}
				formation.setRating(repositoryR.save(newRating));
				jo.put("Formation", repository.save(formation));
				return jo;
			}else {
				if(star == 1) {
					rating.setStar1(rating.getStar1()+1);
				}else if (star == 2) {
					rating.setStar2(rating.getStar2()+1);
				}else if (star == 3) {
					rating.setStar3(rating.getStar3()+1);
				}else if(star == 4) {
					rating.setStar4(rating.getStar4()+1);
				}else if (star == 5 ) {
					rating.setStar5(rating.getStar5()+1);
				}
				
				formation.setRating(repositoryR.save(rating));
				jo.put("Formation", repository.save(formation));
				return jo;
			}
	
			
		}else {
			jo.put("Error", "Formation n'existe pas !");
			return jo;
		}
	}

	/*********************************** AFFICHER LES FORMATIONS PAR USER ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject getFormationsByUser(int id) {
		JSONObject jo = new JSONObject();
		List<Formation> allFormations = repository.findAll();
		
		if(allFormations.size() != 0) {
			ResponseEntity<JSONObject> response = getUserAPI("http://localhost:8181/users/byId", id);
			LinkedHashMap user = (LinkedHashMap) response.getBody().get("User");
			List<Formation> listFormationByUser = new ArrayList<Formation>();

			if (user.get("role").equals("COLLABORATEUR")) {
				
				for(Formation formation : allFormations) {
					if(formation.getListParticipants().stream().filter(p->p.getIdParticipant() == id).findFirst().isPresent()) {
						listFormationByUser.add(formation);
					}
				}
			} else if(user.get("role").equals("TEAMLEAD")) {
				final String uri = "http://localhost:8383/collaborateurs/parTL";
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
				map.add("id", id);
				HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
				ResponseEntity<JSONObject> res = restTemplate.postForEntity( uri, request , JSONObject.class );
				
				ArrayList listUsers = (ArrayList) res.getBody().get("Collaborateurs");
				
				for(Object obj : listUsers) {
					LinkedHashMap object = (LinkedHashMap) obj;
					int idCollaborateur = (int) object.get("idCollaborateur");
					
					for(Formation formation : allFormations) {
						 if(formation.getListParticipants().stream().filter(p->p.getIdParticipant() == idCollaborateur).findFirst().isPresent()) {
							 listFormationByUser.add(formation);
						 }			
					}
				}
				listFormationByUser = listFormationByUser.stream().distinct().collect(Collectors.toList());
			}else {
				final String uri = "http://localhost:8686/teamlead/byManager";
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
				map.add("id", id);
				HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
				ResponseEntity<JSONObject> res2 = restTemplate.postForEntity( uri, request , JSONObject.class );
				
				ArrayList listTeamLead = (ArrayList) res2.getBody().get("TeamLeads");
				for(Object obj : listTeamLead) {
					LinkedHashMap object = (LinkedHashMap) obj;
					int idTeamLead = (int) object.get("idTeamLead");
					final String uri2 = "http://localhost:8383/collaborateurs/parTL";
					MultiValueMap<String, Integer> map2= new LinkedMultiValueMap<String, Integer>();
					map2.add("id", idTeamLead);
					HttpEntity<MultiValueMap<String, Integer>> request2 = new HttpEntity<MultiValueMap<String, Integer>>(map2, headers);
					ResponseEntity<JSONObject> response2 = restTemplate.postForEntity( uri2, request2 , JSONObject.class );
					ArrayList listUsers = (ArrayList) response2.getBody().get("Collaborateurs");
					
					for(Object u : listUsers) {
						LinkedHashMap collaborateur = (LinkedHashMap) u;
						int idCollaborateur = (int) collaborateur.get("idCollaborateur");
						
						for(Formation formation : allFormations) {
							 if(formation.getListParticipants().stream().filter(p->p.getIdParticipant() == idCollaborateur).findFirst().isPresent()) {
								 listFormationByUser.add(formation);
							 }			
						}
					}

				}
				listFormationByUser = listFormationByUser.stream().distinct().collect(Collectors.toList());
			}
			jo.put("Formations", listFormationByUser);
			return jo;
		}else {
			jo.put("Error", "Liste formations est vide !");
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
	
	/*********************************** SET FORMATEUR ***************************************/
	public JSONObject setFormateur(int idFormation,int formateurCabinet) {
		JSONObject jo = new JSONObject();
		if(repository.findById(idFormation).isPresent()) {
			Formation formation = repository.findById(idFormation).get();
			formation.setIdCF(formateurCabinet);
			jo.put("Formation", repository.save(formation));
			return jo;
		}else {
			jo.put("Error", "Formation n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** API ALL COLLABORATEUR ***************************************/
	public ResponseEntity<JSONObject> getAllCollaborateur() {
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JSONObject> response = restTemplate.getForEntity("http://localhost:8181/users/collaborateurs", JSONObject.class );
		return response;
	}
	
	
	
	
	
	
	
	
}
