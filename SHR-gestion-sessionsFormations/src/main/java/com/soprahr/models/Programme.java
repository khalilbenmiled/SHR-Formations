package com.soprahr.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Programme implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private String nom;
	private String description;
	private Date dateDebut;
	private Date dateFin;
	@ManyToOne
	private Session session;
	@OneToMany
	private List<Formation> listFormations;
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
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public List<Formation> getListFormations() {
		return listFormations;
	}
	public void setListFormations(List<Formation> listFormations) {
		this.listFormations = listFormations;
	}
	public Programme(int id, String nom, String description, Date dateDebut, Date dateFin, Session session,
			List<Formation> listFormations) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.session = session;
		this.listFormations = listFormations;
	}
	public Programme() {
		super();
	}
	
	
	
	
	

}
