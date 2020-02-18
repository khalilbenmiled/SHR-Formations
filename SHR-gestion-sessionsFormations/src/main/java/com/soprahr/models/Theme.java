package com.soprahr.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Theme implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private String nom;
	private TypeTheme type;
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
	public TypeTheme getType() {
		return type;
	}
	public void setType(TypeTheme type) {
		this.type = type;
	}
	public Theme(int id, String nom, TypeTheme type) {
		super();
		this.id = id;
		this.nom = nom;
		this.type = type;
	}
	public Theme() {
		super();
	}
	
	
	
	

}
