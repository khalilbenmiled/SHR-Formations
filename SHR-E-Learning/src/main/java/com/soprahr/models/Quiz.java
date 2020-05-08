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
public class Quiz implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private int idFormation;
	private String nomQuiz;
	private int nbrQuestion;
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Question> listQuestions = new ArrayList<Question>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Question> getListQuestions() {
		return listQuestions;
	}
	public void setListQuestions(List<Question> listQuestions) {
		this.listQuestions = listQuestions;
	}
	public int getIdFormation() {
		return idFormation;
	}
	public void setIdFormation(int idFormation) {
		this.idFormation = idFormation;
	}
	public String getNomQuiz() {
		return nomQuiz;
	}
	public void setNomQuiz(String nomQuiz) {
		this.nomQuiz = nomQuiz;
	}
	public int getNbrQuestion() {
		return nbrQuestion;
	}
	public void setNbrQuestion(int nbrQuestion) {
		this.nbrQuestion = nbrQuestion;
	}

	public Quiz(int id, int idFormation, String nomQuiz, int nbrQuestion, List<Question> listQuestions) {
		super();
		this.id = id;
		this.idFormation = idFormation;
		this.nomQuiz = nomQuiz;
		this.nbrQuestion = nbrQuestion;
		this.listQuestions = listQuestions;
	}
	public Quiz() {
		super();
	}
	
	

}
