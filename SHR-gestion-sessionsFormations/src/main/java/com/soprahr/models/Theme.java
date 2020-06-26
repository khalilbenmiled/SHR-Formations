package com.soprahr.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	@Enumerated(EnumType.STRING)
	private TypeTheme type;
	@OneToMany
	private List<Module> listModules;
	private boolean deleted;
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
	public List<Module> getListModules() {
		return listModules;
	}
	public void setListModules(List<Module> listModules) {
		this.listModules = listModules;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Theme(int id, String nom, TypeTheme type, List<Module> listModules) {
		super();
		this.id = id;
		this.nom = nom;
		this.type = type;
		this.listModules = listModules;
	}
	public Theme() {
		super();
	}
	
	
	
	

}
