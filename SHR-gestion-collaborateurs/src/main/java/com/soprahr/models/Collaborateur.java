package com.soprahr.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Collaborateur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int idCollaborateur;
	private int idTeamLeader;
	@OneToMany
	private List<Competence> listCompetences;
	@OneToOne
	private Parcour parcour;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdCollaborateur() {
		return idCollaborateur;
	}
	public void setIdCollaborateur(int idCollaborateur) {
		this.idCollaborateur = idCollaborateur;
	}
	public List<Competence> getListCompetences() {
		return listCompetences;
	}
	public void setListCompetences(List<Competence> listCompetences) {
		this.listCompetences = listCompetences;
	}
	public Parcour getParcour() {
		return parcour;
	}
	public void setParcour(Parcour parcour) {
		this.parcour = parcour;
	}
	public int getIdTeamLeader() {
		return idTeamLeader;
	}
	public void setIdTeamLeader(int idTeamLeader) {
		this.idTeamLeader = idTeamLeader;
	}
	public Collaborateur(int id, int idCollaborateur, int idTeamLeader, List<Competence> listCompetences,
			Parcour parcour) {
		super();
		this.id = id;
		this.idCollaborateur = idCollaborateur;
		this.idTeamLeader = idTeamLeader;
		this.listCompetences = listCompetences;
		this.parcour = parcour;
	}
	public Collaborateur() {
		super();
	}
	

	
}
