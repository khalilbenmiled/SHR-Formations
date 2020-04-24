package com.soprahr.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ModulesFormation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nom;
	private String description;
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ModulesFormation(String nom, String description) {
		super();
		this.nom = nom;
		this.description = description;
	}
	public ModulesFormation() {
		super();
	}
	

}
