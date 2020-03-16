package com.soprahr.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Projet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private String nom; 
	private int idTeamLead;
	public Projet(int id, String nom, int idTeamLead) {
		super();
		this.id = id;
		this.nom = nom;
		this.idTeamLead = idTeamLead;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getIdTeamLead() {
		return idTeamLead;
	}
	public void setIdTeamLead(int idTeamLead) {
		this.idTeamLead = idTeamLead;
	}
	public Projet() {
		super();
	}
	
	

}
