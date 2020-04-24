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
	private int idManager;
	
	public Projet(int id, String nom, int idTeamLead, int idManager) {
		super();
		this.id = id;
		this.nom = nom;
		this.idTeamLead = idTeamLead;
		this.idManager = idManager;
	}
	public int getIdManager() {
		return idManager;
	}
	public void setIdManager(int idManager) {
		this.idManager = idManager;
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
