package com.soprahr.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Collaborateur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String tel;
	private String bu;
	@OneToMany
	private List<Competence> listCompetences;
	@OneToOne
	private Parcour parcour;
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
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getBu() {
		return bu;
	}
	public void setBu(String bu) {
		this.bu = bu;
	}
	
	public List<Competence> getListCompetences() {
		return listCompetences;
	}
	public void setListCompetences(List<Competence> listCompetences) {
		this.listCompetences = listCompetences;
	}
	
	public Parcour getParcour() {
		return parcour;
	}
	public void setParcour(Parcour parcour) {
		this.parcour = parcour;
	}

	public Collaborateur(int id, String nom, String prenom, String email, String tel, String bu,
			List<Competence> listCompetences, Parcour parcour) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.tel = tel;
		this.bu = bu;
		this.listCompetences = listCompetences;
		this.parcour = parcour;
	}
	public Collaborateur() {
		super();
	}
	
	

	
}
