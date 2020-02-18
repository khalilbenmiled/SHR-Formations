package com.soprahr.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Domaine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	private int id;
	private String description;
	@ManyToMany(mappedBy = "listDomaines")
	@JsonIgnore
	private List<Cabinet> listCabinets;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Cabinet> getListCabinets() {
		return listCabinets;
	}
	public void setListCabinets(List<Cabinet> listCabinets) {
		this.listCabinets = listCabinets;
	}
	public Domaine() {
		super();
	}
	public Domaine(int id, String description, List<Cabinet> listCabinets) {
		super();
		this.id = id;
		this.description = description;
		this.listCabinets = listCabinets;
	}
	
	
	
	

}
