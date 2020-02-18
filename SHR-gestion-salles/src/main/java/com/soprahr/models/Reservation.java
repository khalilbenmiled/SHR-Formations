package com.soprahr.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Reservation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	@OneToOne
	private Salle salle;
	@OneToOne
	private Programme programme;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Salle getSalle() {
		return salle;
	}
	public void setSalle(Salle salle) {
		this.salle = salle;
	}
	public Programme getProgramme() {
		return programme;
	}
	public void setProgramme(Programme programme) {
		this.programme = programme;
	}
	public Reservation(int id, Salle salle, Programme programme) {
		super();
		this.id = id;
		this.salle = salle;
		this.programme = programme;
	}
	public Reservation() {
		super();
	}
	
	

}
