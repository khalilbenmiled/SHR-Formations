package com.soprahr.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Salle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private int numero;
	private int etage;
	@OneToMany
	private List<Programme> listprogrammes = new ArrayList<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getEtage() {
		return etage;
	}
	public void setEtage(int etage) {
		this.etage = etage;
	}
	

	public Salle(int id, int numero, int etage, List<Programme> listprogrammes) {
		super();
		this.id = id;
		this.numero = numero;
		this.etage = etage;
		this.listprogrammes = listprogrammes;
	}
	public List<Programme> getListprogrammes() {
		return listprogrammes;
	}
	public void setListprogrammes(List<Programme> listprogrammes) {
		this.listprogrammes = listprogrammes;
	}
	public Salle() {
		super();
	}
	
	
	

	
}
