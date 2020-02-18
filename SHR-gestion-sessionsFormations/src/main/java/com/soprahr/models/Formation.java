package com.soprahr.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Formation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private String nom;
	private String description;
	private String niveauRequis;
	private String niveauObtenu;
	private float duree;
	private String type;
	private float prix;
	@OneToMany
	private List<Module> listModules;
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
	public String getNiveauRequis() {
		return niveauRequis;
	}
	public void setNiveauRequis(String niveauRequis) {
		this.niveauRequis = niveauRequis;
	}
	public String getNiveauObtenu() {
		return niveauObtenu;
	}
	public void setNiveauObtenu(String niveauObtenu) {
		this.niveauObtenu = niveauObtenu;
	}
	public float getDuree() {
		return duree;
	}
	public void setDuree(float duree) {
		this.duree = duree;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Module> getListModules() {
		return listModules;
	}
	public void setListModules(List<Module> listModules) {
		this.listModules = listModules;
	}
	
	public float getPrix() {
		return prix;
	}
	public void setPrix(float prix) {
		this.prix = prix;
	}
	
	public Formation(int id, String nom, String description, String niveauRequis, String niveauObtenu, float duree,
			String type, float prix, List<Module> listModules) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.niveauRequis = niveauRequis;
		this.niveauObtenu = niveauObtenu;
		this.duree = duree;
		this.type = type;
		this.prix = prix;
		this.listModules = listModules;
	}
	public Formation() {
		super();
	}
	
	
	

	
}
