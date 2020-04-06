package com.soprahr.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Formation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private Date dateDebut;
	private Date dateFin;
	private String nomTheme;
	@Enumerated(EnumType.STRING)
	private TypeTheme typeTheme;
	@OneToMany
	private List<Module> listModules = new ArrayList<Module>();
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

	public List<Module> getListModules() {
		return listModules;
	}
	public void setListModules(List<Module> listModules) {
		this.listModules = listModules;
	}
	public TypeTheme getTypeTheme() {
		return typeTheme;
	}
	public void setTypeTheme(TypeTheme typeTheme) {
		this.typeTheme = typeTheme;
	}
	
	public Formation(int id, Date dateDebut, Date dateFin, String nomTheme, TypeTheme typeTheme,
			List<Module> listModules, int maxParticipants, float duree, float prix, EtatFormation etat, int idCF) {
		super();
		this.id = id;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.nomTheme = nomTheme;
		this.typeTheme = typeTheme;
		this.listModules = listModules;
		this.maxParticipants = maxParticipants;
		this.duree = duree;
		this.prix = prix;
		this.etat = etat;
		this.idCF = idCF;
	}
	public Formation() {
		super();
	}
	
	
	

	
}
