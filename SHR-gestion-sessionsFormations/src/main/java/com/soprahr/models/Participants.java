package com.soprahr.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Participants implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idParticipant;

	public int getIdParticipant() {
		return idParticipant;
	}

	public void setIdParticipant(int idParticipant) {
		this.idParticipant = idParticipant;
	}

	public Participants(int idParticipant) {
		super();
		this.idParticipant = idParticipant;
	}

	public Participants() {
		super();
	}
	

}
