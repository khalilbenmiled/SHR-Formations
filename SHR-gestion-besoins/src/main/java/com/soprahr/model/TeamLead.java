package com.soprahr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TeamLead implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private int idTeamLead;
	@OneToMany
	private List<Projet> listProjets = new ArrayList<>();
	@OneToMany
	private List<Besoins> listBesoins = new ArrayList<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdTeamLead() {
		return idTeamLead;
	}
	public void setIdTeamLead(int idTeamLead) {
		this.idTeamLead = idTeamLead;
	}
	public List<Projet> getListProjets() {
		return listProjets;
	}
	public void setListProjets(List<Projet> listProjets) {
		this.listProjets = listProjets;
	}
	public List<Besoins> getListBesoins() {
		return listBesoins;
	}
	public void setListBesoins(List<Besoins> listBesoins) {
		this.listBesoins = listBesoins;
	}
	public TeamLead(int id, int idTeamLead, List<Projet> listProjets, List<Besoins> listBesoins) {
		super();
		this.id = id;
		this.idTeamLead = idTeamLead;
		this.listProjets = listProjets;
		this.listBesoins = listBesoins;
	} 
	
	
	

}
