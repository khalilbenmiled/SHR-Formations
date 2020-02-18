package com.soprahr.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Competence implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	
	private String nom;
	private String degre;
	private String type;
	private String description;
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
	public String getDegre() {
		return degre;
	}
	public void setDegre(String degre) {
		this.degre = degre;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Competence(int id, String nom, String degre, String type, String description) {
		super();
		this.id = id;
		this.nom = nom;
		this.degre = degre;
		this.type = type;
		this.description = description;
	}
	public Competence() {
		super();
	}
	
	
	
	

}
