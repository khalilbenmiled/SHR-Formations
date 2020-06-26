package com.soprahr.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
public class Formation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	@Temporal(value = TemporalType.DATE)
	private Date dateDebut;
	@Temporal(value = TemporalType.DATE)
	private Date dateFin;
	private String nomTheme;
	private String typeTheme;
	@ElementCollection
	private List<ModulesFormation> listModules = new ArrayList<ModulesFormation>();
	private int maxParticipants; // nombre de participants MAX
	@ElementCollection
	private List<Participants> listParticipants = new ArrayList<Participants>();
	private int duree; // duree ce cette formation
	private float prix;
	private EtatFormation etat; 
	private int idCF; // cabinet ou formateur de cette formation
	@OneToOne
	private Rating rating;
	private boolean deleted;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMaxParticipants() {
		return maxParticipants;
	}
	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
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
	public String getNomTheme() {
		return nomTheme;
	}
	public void setNomTheme(String nomTheme) {
		this.nomTheme = nomTheme;
	}
	public String getTypeTheme() {
		return typeTheme;
	}
	public void setTypeTheme(String typeTheme) {
		this.typeTheme = typeTheme;
	}
	public List<ModulesFormation> getListModules() {
		return listModules;
	}
	public void setListModules(List<ModulesFormation> listModules) {
		this.listModules = listModules;
	}
	public List<Participants> getListParticipants() {
		return listParticipants;
	}
	public void setListParticipants(List<Participants> listParticipants) {
		this.listParticipants = listParticipants;
	}

	public Rating getRating() {
		return rating;
	}
	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Formation(int id, Date dateDebut, Date dateFin, String nomTheme, String typeTheme,
			List<ModulesFormation> listModules, int maxParticipants, List<Participants> listParticipants, int duree,
			float prix, EtatFormation etat, int idCF, Rating rating) {
		super();
		this.id = id;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.nomTheme = nomTheme;
		this.typeTheme = typeTheme;
		this.listModules = listModules;
		this.maxParticipants = maxParticipants;
		this.listParticipants = listParticipants;
		this.duree = duree;
		this.prix = prix;
		this.etat = etat;
		this.idCF = idCF;
		this.rating = rating;
	}
	public Formation() {
		super();
	}
	
	
	

	
}
