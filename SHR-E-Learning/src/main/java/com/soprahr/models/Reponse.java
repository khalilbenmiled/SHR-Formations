package com.soprahr.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Reponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private String reponse;
	private boolean correcte;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReponse() {
		return reponse;
	}
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
	public boolean isCorrecte() {
		return correcte;
	}
	public void setCorrecte(boolean correcte) {
		this.correcte = correcte;
	}
	public Reponse( String reponse, boolean correcte) {
		super();
		
		this.reponse = reponse;
		this.correcte = correcte;
	}
	public Reponse() {
		super();
	}
	
	

}
