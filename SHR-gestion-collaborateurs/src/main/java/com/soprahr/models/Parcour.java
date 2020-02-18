package com.soprahr.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Parcour implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	@OneToOne
	private Collaborateur collaborateur;
	@OneToMany
	private List<Formation> listFormations;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Collaborateur getCollaborateur() {
		return collaborateur;
	}
	public void setCollaborateur(Collaborateur collaborateur) {
		this.collaborateur = collaborateur;
	}
	
	public List<Formation> getListFormations() {
		return listFormations;
	}
	public void setListFormations(List<Formation> listFormations) {
		this.listFormations = listFormations;
	}

	public Parcour(int id, Collaborateur collaborateur, List<Formation> listFormations) {
		super();
		this.id = id;
		this.collaborateur = collaborateur;
		this.listFormations = listFormations;
	}
	public Parcour() {
		super();
	}
	
	
	

}
