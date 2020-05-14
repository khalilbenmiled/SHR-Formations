package com.soprahr.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Score implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	@OneToOne
	private Quiz quiz;
	private int idCollaborateur;
	private float resultat;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	public int getIdCollaborateur() {
		return idCollaborateur;
	}
	public void setIdCollaborateur(int idCollaborateur) {
		this.idCollaborateur = idCollaborateur;
	}
	public float getResultat() {
		return resultat;
	}
	public void setResultat(float resultat) {
		this.resultat = resultat;
	}
	public Score(int id, Quiz quiz, int idCollaborateur, float resultat) {
		super();
		this.id = id;
		this.quiz = quiz;
		this.idCollaborateur = idCollaborateur;
		this.resultat = resultat;
	}
	public Score() {
		super();
	}
	

	
}
