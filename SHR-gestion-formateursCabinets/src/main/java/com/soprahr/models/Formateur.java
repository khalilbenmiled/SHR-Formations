package com.soprahr.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Formateur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String adresse;
	private String tel;
	private int expertise;
	private BU bu;
	@ManyToOne
	@JsonIgnore
	private Cabinet cabinet;

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

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getExpertise() {
		return expertise;
	}

	public void setExpertise(int expertise) {
		this.expertise = expertise;
	}

	public BU getBu() {
		return bu;
	}

	public void setBu(BU bu) {
		this.bu = bu;
	}

	public Cabinet getCabinet() {
		return cabinet;
	}

	public void setCabinet(Cabinet cabinet) {
		this.cabinet = cabinet;
	}

	public Formateur(int id, String nom, String prenom, String email, String adresse, String tel, int expertise, BU bu,
			Cabinet cabinet) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.adresse = adresse;
		this.tel = tel;
		this.expertise = expertise;
		this.bu = bu;
		this.cabinet = cabinet;
	}

	public Formateur() {
		super();
	}

}
