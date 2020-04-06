package com.soprahr.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Embeddable
public class Theme implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nom;
	@Enumerated(EnumType.STRING)
	private TypeTheme type;
	@ElementCollection
	private List<Module> listModules;

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
	public Theme(String nom, TypeTheme type, List<Module> listModules) {
		super();
		this.nom = nom;
		this.type = type;
		this.listModules = listModules;
	}
	public Theme() {
		super();
	}
	
	
	
	

}
