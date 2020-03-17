package com.soprahr.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


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
	private int idManager;
	
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
	public int getIdManager() {
		return idManager;
	}
	public void setIdManager(int idManager) {
		this.idManager = idManager;
	}
	public TeamLead(int id, int idTeamLead, int idManager) {
		super();
		this.id = id;
		this.idTeamLead = idTeamLead;
		this.idManager = idManager;
	}
	public TeamLead() {
		super();
	}
	
	
	

}
