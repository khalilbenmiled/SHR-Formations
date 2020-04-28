package com.soprahr.Services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.RabbitMQ.RabbitMQSender;
import com.soprahr.Repository.CabinetRepository;
import com.soprahr.Repository.DomaineRepository;
import com.soprahr.Repository.FormateurRepository;
import com.soprahr.models.Cabinet;
import com.soprahr.models.Domaine;
import com.soprahr.models.Formateur;

import net.minidev.json.JSONObject;

@Service
public class CabinetServices {

	@Autowired
	public CabinetRepository repositoryC;
	@Autowired
	public FormateurRepository repositoryF;
	@Autowired
	public DomaineRepository repositoryD;
	@Autowired
	public RabbitMQSender rabbitMQSender;
	
	/*********************************** AJOUTER UN CABINET ***************************************/
	public JSONObject addCabinet(Cabinet cabinet) {
		JSONObject jo = new JSONObject();
		jo.put("cabinet",repositoryC.save(cabinet));
		return jo;
	}
	
	/*********************************** LISTE CABINETS ***************************************/
	public JSONObject getAllCabinets() {
		JSONObject jo = new JSONObject();
		if ( repositoryC.findAll().size() != 0 ) {
			jo.put("cabinets" , repositoryC.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des cabinets est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UN CABINET ***************************************/
	public JSONObject deleteCabinet(int id) {
		JSONObject jo = new JSONObject();
		if(repositoryC.findById(id).isPresent()) {
			
//			/*------------------ DECLENCHER UN EVENEMENT AUX AUTRES SERICES --------------------------------*/
//			JSONObject object = new JSONObject();
//			object.put("Cabinet-supprime", repositoryC.findById(id).get());
//			rabbitMQSender.send(object);
//			/*----------------------------------------------------------------------------------------------*/
			
			repositoryC.delete(repositoryC.findById(id).get());
			jo.put("Success", "Cabinet supprimé");
			return jo;
		}else {
			jo.put("Error" , "Cabinet n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UN CABINET PAR ID ***************************************/
	public JSONObject getCabinetById(int id) {
		JSONObject jo = new JSONObject();
		if(repositoryC.findById(id).isPresent()) {
			jo.put("Cabinet", repositoryC.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Cabinet n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UN CABINET OU FORMATEUR PAR ID ***************************************/
	public JSONObject getCabinetOrFormateur(int id) {
		JSONObject jo = new JSONObject();
		if(repositoryC.findById(id).isPresent()) {
			jo.put("Cabinet", repositoryC.findById(id).get());
			return jo;
		}else if (repositoryF.findById(id).isPresent()) {
			jo.put("Formateur", repositoryF.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Cabinet n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** AJOUTER UN FORMATEUR A UN CABINET ***************************************/
	public JSONObject addFormateurCabinet(int idFormateur,int idCabinet) {
		JSONObject jo = new JSONObject();
		if(repositoryC.findById(idCabinet).isPresent()) {
			Cabinet cabinet = repositoryC.findById(idCabinet).get();
			if(repositoryF.findById(idFormateur).isPresent()) {
				Formateur formateur = repositoryF.findById(idFormateur).get();
				List<Formateur> listFormateurs = cabinet.getListFormateurs();
				if(!listFormateurs.contains(formateur)) {
					listFormateurs.add(formateur);
					cabinet.setListFormateurs(listFormateurs);
					repositoryC.save(cabinet);
					formateur.setCabinet(cabinet);
					repositoryF.save(formateur);
					jo.put("Success", "Formateur ajouté !");
					return jo;
				}else {
					jo.put("Error" , "Formateur existe deja dans cet cabinet !");
					return jo;
				}	
			}else {
				jo.put("Error" , "Formateur n'existe pas !");
				return jo;
			}
		}else {
			jo.put("Error", "Cabinet n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** AJOUTER UN FORMATEUR A UN CABINET ***************************************/
	public JSONObject addDomaineCabinet(int idDomaine, int idCabinet) {
		JSONObject jo = new JSONObject();
		if(repositoryC.findById(idCabinet).isPresent()) {
			Cabinet cabinet = repositoryC.findById(idCabinet).get();
			if(repositoryD.findById(idDomaine).isPresent()) {
				Domaine domaine = repositoryD.findById(idDomaine).get();
				if(!cabinet.getListDomaines().contains(domaine)) {
					List<Domaine> listDomaines = cabinet.getListDomaines();
					listDomaines.add(domaine);
					cabinet.setListDomaines(listDomaines);
					repositoryC.save(cabinet);
					jo.put("Success", cabinet);
					return jo;
				}else {
					jo.put("Error", "Domaine existe deja dans ce cabinet !");
					return jo;
				}
			}else {
				jo.put("Error", "Domaine n'existe pas !");
				return jo;
			}
		}else {
			jo.put("Error", "Cabinet n'existe pas !");
			return jo;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
