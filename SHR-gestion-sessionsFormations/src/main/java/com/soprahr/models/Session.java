package com.soprahr.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Session implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private String nom;
	private String description;
	private int trimestre;
	@OneToMany
	private List<Formation> listFormations = new ArrayList<>();

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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTrimestre() {
		return trimestre;
	}
	public void setTrimestre(int trimestre) {
		this.trimestre = trimestre;
	}
	public List<Formation> getListFormations() {
		return listFormations;
	}
	public void setListFormations(List<Formation> listFormations) {
		this.listFormations = listFormations;
	}
	public Session() {
		super();
	}
	public Session(int id, String nom, String description, int trimestre,
			List<Formation> listFormations) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.trimestre = trimestre;
		this.listFormations = listFormations;
	}

	
	

}
