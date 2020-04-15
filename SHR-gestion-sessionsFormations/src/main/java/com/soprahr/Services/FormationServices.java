package com.soprahr.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soprahr.RabbitMQ.RabbitMQSender;
import com.soprahr.Repository.FormationRepository;
import com.soprahr.Repository.ModuleRepository;
import com.soprahr.Repository.SessionRepository;
import com.soprahr.Repository.ThemeRepository;
import com.soprahr.models.Formation;
import com.soprahr.models.Module;
import com.soprahr.models.Session;
import com.soprahr.models.Theme;	

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
	public JSONObject ajouterFormation(String nomTheme , String typeTheme ,String dateDebutStr, String dateFinStr , int maxParticipants, float duree,int idSession,int quarter) {
		JSONObject jo = new JSONObject();
		Formation f = new Formation();
		try {
			
			
			if (repositoryS.findById(idSession).isPresent()) {
				Date dateDebut=new SimpleDateFormat("dd/MM/yy HH:mm" ).parse(dateDebutStr);
				Date dateFin=new SimpleDateFormat("dd/MM/yy HH:mm" ).parse(dateFinStr);
				
				
				Theme theme = repositoryT.getThemeByNomAndType(nomTheme, typeTheme);
				f.setTheme(theme);
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
	
	/*********************************** AJOUTER LIST MODULES A UNE FORMATION ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject setListModulesToFormation(JSONObject listModules) {
		JSONObject jo = new JSONObject();
		ArrayList array = (ArrayList) listModules.get("modules");
		int idFormation = (int) listModules.get("idFormation");
		
		ArrayList modules = (ArrayList) array.get(0);
		List<Module> listToSet = new ArrayList<Module>();
		
		for (Object object : modules) {
			LinkedHashMap obj = (LinkedHashMap) object;
			String nomModule = (String) obj.get("nom");
			String descriptionModule = (String) obj.get("description");
			Module module = repositoryM.getModuleByNomAndDescription(nomModule,descriptionModule);
			listToSet.add(module);
		}
		if (repository.findById(idFormation).isPresent()) {
			Formation formation = repository.findById(idFormation).get();
			formation.getTheme().setListModules(listToSet);
			jo.put("Formation" , repository.save(formation));
			return jo;
		}else {
			jo.put("Error" , "Formation n'existe pas !");
			return jo;
		}
		
		
	}
	
	
	
	
	
	
	
}
