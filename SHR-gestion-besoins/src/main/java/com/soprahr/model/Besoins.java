package com.soprahr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.soprahr.model.BU;

@Entity
public class Besoins implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	@Enumerated(EnumType.STRING)
	private BU bu;
	private int quarter;
	private int annee;
	private int nbrPrevu;
	private int priorite;
	private boolean validerTL;
	private boolean validerMG;
	@OneToOne
	private Projet projet;
	@Embedded
	private Theme theme;
	@ElementCollection
	private List<Participants> listParticipants = new ArrayList<Participants>();
	private int idUser;
	private boolean publier;
	private boolean planifier;
	private boolean sendToSF;
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BU getBu() {
		return bu;
	}
	public void setBu(BU bu) {
		this.bu = bu;
	}
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public int getNbrPrevu() {
		return nbrPrevu;
	}
	public void setNbrPrevu(int nbrPrevu) {
		this.nbrPrevu = nbrPrevu;
	}
	public int getPriorite() {
		return priorite;
	}
	public void setPriorite(int priorite) {
		this.priorite = priorite;
	}
	public Projet getProjet() {
		return projet;
	}
	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public Besoins() {
		super();
	}
	public boolean isValiderTL() {
		return validerTL;
	}
	public void setValiderTL(boolean validerTL) {
		this.validerTL = validerTL;
	}
	public boolean isValiderMG() {
		return validerMG;
	}
	public void setValiderMG(boolean validerMG) {
		this.validerMG = validerMG;
	}
	public boolean isPublier() {
		return publier;
	}
	public void setPublier(boolean publier) {
		this.publier = publier;
	}
	public boolean isPlanifier() {
		return planifier;
	}
	public void setPlanifier(boolean planifier) {
		this.planifier = planifier;
	}
	public List<Participants> getListParticipants() {
		return listParticipants;
	}
	public void setListParticipants(List<Participants> listParticipants) {
		this.listParticipants = listParticipants;
	}
	public boolean isSendToSF() {
		return sendToSF;
	}
	public void setSendToSF(boolean sendToSF) {
		this.sendToSF = sendToSF;
	}
	
	public int getAnnee() {
		return annee;
	}
	public void setAnnee(int annee) {
		this.annee = annee;
	}
	public Besoins(int id, BU bu, int quarter, int nbrPrevu, int priorite, boolean validerTL, boolean validerMG,
			Projet projet, Theme theme, List<Participants> listParticipants, int idUser, boolean publier,
			boolean planifier, boolean sendToSF) {
		super();
		this.id = id;
		this.bu = bu;
		this.quarter = quarter;
		this.nbrPrevu = nbrPrevu;
		this.priorite = priorite;
		this.validerTL = validerTL;
		this.validerMG = validerMG;
		this.projet = projet;
		this.theme = theme;
		this.listParticipants = listParticipants;
		this.idUser = idUser;
		this.publier = publier;
		this.planifier = planifier;
		this.sendToSF = sendToSF;
	}


}









