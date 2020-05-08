package com.soprahr.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Question implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private String question;
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Reponse> listReponses = new ArrayList<Reponse>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<Reponse> getListReponses() {
		return listReponses;
	}
	public void setListReponses(List<Reponse> listReponses) {
		this.listReponses = listReponses;
	}
	public Question(int id, String question, List<Reponse> listReponses) {
		super();
		this.id = id;
		this.question = question;
		this.listReponses = listReponses;
	}
	public Question() {
		super();
	}
	
	
	

}
