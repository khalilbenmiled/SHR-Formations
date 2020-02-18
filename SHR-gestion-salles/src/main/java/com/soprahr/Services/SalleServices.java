package com.soprahr.Services;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soprahr.Repository.ProgrammeRepository;
import com.soprahr.Repository.ReservationRepository;
import com.soprahr.Repository.SalleRepository;
import com.soprahr.models.Programme;
import com.soprahr.models.Reservation;
import com.soprahr.models.Salle;
import com.soprahr.utils.Utils;
import net.minidev.json.JSONObject;

@Service
public class SalleServices {

	@Autowired
	public SalleRepository repositoryS;
	@Autowired
	public ProgrammeRepository repositoryP;
	@Autowired
	public ReservationRepository repositoryR;

	
	/*********************************** AJOUTER UNE SALLE ***************************************/
	public JSONObject addSalle(Salle salle) {
		JSONObject jo = new JSONObject();
		jo.put("Salle",repositoryS.save(salle));
		return jo;
	}
	
	/*********************************** LISTE SALLES ***************************************/
	public JSONObject getAllSalles() {
		JSONObject jo = new JSONObject();
		if ( repositoryS.findAll().size() != 0 ) {
			jo.put("Salles" , repositoryS.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des salles est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UNE SALLE ***************************************/
	public JSONObject deleteSalle(int id) {
		JSONObject jo = new JSONObject();
		if(repositoryS.findById(id).isPresent()) {
			repositoryS.delete(repositoryS.findById(id).get());
			jo.put("Success", "Salle supprimé");
			return jo;
		}else {
			jo.put("Error" , "Salle n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UNE SALLE PAR ID ***************************************/
	public JSONObject getSalleById(int id) {
		JSONObject jo = new JSONObject();
		if(repositoryS.findById(id).isPresent()) {
			jo.put("Salle", repositoryS.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Salle n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** AJOUTER UN PROGRAMME A UNE SALLE ***************************************/
	public JSONObject addProgrammeSalle(int idProgramme, int idSalle) {
		JSONObject jo = new JSONObject();
		if(repositoryS.findById(idSalle).isPresent()) {
			Salle salle = repositoryS.findById(idSalle).get();
			if(repositoryP.findById(idProgramme).isPresent()) {
				List<Programme> listProgrammes = salle.getListprogrammes();
				if(!listProgrammes.contains(repositoryP.findById(idProgramme).get())) {
					listProgrammes.add(repositoryP.findById(idProgramme).get());
					salle.setListprogrammes(listProgrammes);
					repositoryS.save(salle);
					jo.put("Success", salle);
					return jo;
				}else {
					jo.put("Error", "Programme existe deja a cette salle !");
					return jo;
				}
			}else {
				jo.put("Error", "Programme n'existe pas !");
				return jo;
			}
		}else {
			jo.put("Error", "Salle n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RESERVER UNE SALLE ***************************************/
	public JSONObject reserverSalle(int idSalle,int idProgramme) {
		JSONObject jo = new JSONObject();
		if(repositoryS.findById(idSalle).isPresent()) {
			Salle salle = repositoryS.findById(idSalle).get();
			if(repositoryP.findById(idProgramme).isPresent()) {
				Programme programme = repositoryP.findById(idProgramme).get();
				
				/*------------------- VERIFIER LA RESERVATION -------------------------------*/
				List<Reservation> listReservations = repositoryR.findAll();
				for(Reservation r : listReservations) {
					if(r.getProgramme().equals(programme) && r.getSalle().equals(salle)) {
						jo.put("Error", "Vous avez deja reservez cette salle !");
						return jo;
					}
				}
				/*------------------- ------------------------------- -----------------------*/
				
				Reservation reservation = new Reservation();
				reservation.setSalle(salle);
				programme.setDisponible(false);
				repositoryP.save(programme);
				reservation.setProgramme(programme);
				repositoryR.save(reservation);
				jo.put("Success", reservation);
				return jo;
			}else {
				jo.put("Error", "Programme n'existe pas !");
				return jo;
			}
		}else {
			jo.put("Error", "Salle n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** DISPONIBILITE D'UNE SALLE ***************************************/
	public JSONObject verifDispoSalle(int idSalle,String sDate) {
		JSONObject jo = new JSONObject();
		Utils utils = new Utils();
		Date date1 = utils.convertStringToDate(sDate);

		if(repositoryS.findById(idSalle).isPresent()) {
			Salle salle = repositoryS.findById(idSalle).get();
			List<Programme> listProgrammes = salle.getListprogrammes();
			if (listProgrammes.size() != 0) {
				for (Programme programme : listProgrammes) {
		
					if(utils.dateEquals(date1, programme.getDateDebut()) && programme.isDisponible()) {
						jo.put("Success", "La salle est disponible !");
						return jo;
					}
				}
				jo.put("Error", "La salle n'est pas disponible !");
				return jo;
			}else {
				jo.put("Error", "Pas de programmes pour cette salle !");
				return jo;
			}
		}else {
			jo.put("Error", "Salle n'existe pas");
			return jo;
		}	
	}	
	
}
