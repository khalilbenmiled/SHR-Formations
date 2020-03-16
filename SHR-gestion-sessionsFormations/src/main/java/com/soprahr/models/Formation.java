package com.soprahr.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Formation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private BU bu;
	private Date dateDebut;
	private Date dateFin;
	@OneToOne
	private Theme theme;
	private int maxParticipants; // nombre de participants MAX
	private float duree; // duree ce cette formation
	private float prix;
	private EtatFormation etat;
	private int idCF; // cabinet ou formateur de cette formation
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	public int getMaxParticipants() {
		return maxParticipants;
	}
	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}
	public float getDuree() {
		return duree;
	}
	public void setDuree(float duree) {
		this.duree = duree;
	}
	public float getPrix() {
		return prix;
	}
	public void setPrix(float prix) {
		this.prix = prix;
	}
	public EtatFormation getEtat() {
		return etat;
	}
	public void setEtat(EtatFormation etat) {
		this.etat = etat;
	}
	public BU getBu() {
		return bu;
	}
	public void setBu(BU bu) {
		this.bu = bu;
	}
	public int getIdCF() {
		return idCF;
	}
	public void setIdCF(int idCF) {
		this.idCF = idCF;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public Formation(int id, BU bu, Date dateDebut, Date dateFin, Theme theme, int maxParticipants,
			 float duree, float prix, EtatFormation etat, int idCF) {
		super();
		this.id = id;
		this.bu = bu;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.theme = theme;
		this.maxParticipants = maxParticipants;
		this.duree = duree;
		this.prix = prix;
		this.etat = etat;
		this.idCF = idCF;
	}
	public Formation() {
		super();
	}
	@Override
	public String toString() {
		return "Formation [id=" + id + ", bu=" + bu + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", theme="
				+ theme + ", maxParticipants=" + maxParticipants  + ", duree=" + duree
				+ ", prix=" + prix + ", etat=" + etat + ", idCF=" + idCF + "]";
	}
	
	
	

	
}
