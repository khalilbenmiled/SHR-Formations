package com.soprahr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.ReservationRepository;
import com.soprahr.models.Reservation;
import net.minidev.json.JSONObject;

@Service
public class ReservationServices {

	@Autowired
	public ReservationRepository repository;
	
	/*********************************** AJOUTER UNE Reservation ***************************************/
	public JSONObject addReservation(Reservation reservation) {
		JSONObject jo = new JSONObject();
		jo.put("Reservation",repository.save(reservation));
		return jo;
	}
	
	/*********************************** LISTE RESERVATIONS ***************************************/
	public JSONObject getAllReservations() {
		JSONObject jo = new JSONObject();
		if ( repository.findAll().size() != 0 ) {
			jo.put("Reservations" , repository.findAll());
			return jo;
		}else {
			jo.put("Error" , "La liste des reservations est vide");
			return jo;
		}
	}
	
	/*********************************** SUPPRIMER UNE RESERVATION ***************************************/
	public JSONObject deleteReservation(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			repository.delete(repository.findById(id).get());
			jo.put("Success", "Reservation supprimé");
			return jo;
		}else {
			jo.put("Error" , "Reservation n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RECHERCHER UNE RESERVATION PAR ID ***************************************/
	public JSONObject getReservationById(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			jo.put("Reservation", repository.findById(id).get());
			return jo;
		}else {
			jo.put("Error" , "Reservation n'existe pas !");
			return jo;
		}
	}
}
