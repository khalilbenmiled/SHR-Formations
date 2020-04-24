package com.soprahr.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Participants implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idParticipant;
	private boolean participe;

	public int getIdParticipant() {
		return idParticipant;
	}

	public void setIdParticipant(int idParticipant) {
		this.idParticipant = idParticipant;
	}
	

	public boolean isParticipe() {
		return participe;
	}

	public void setParticipe(boolean participe) {
		this.participe = participe;
	}



	public Participants(int idParticipant, boolean participe) {
		super();
		this.idParticipant = idParticipant;
		this.participe = participe;
	}

	public Participants() {
		super();
	}
	

}
