package com.soprahr.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Besoin implements Serializable {

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
	private String type; //Présence ou e-learnig
	private int nbrPrevu; //nombre de participants prévu
	private String recommendation; //cabinet ou formateur
	private int priorite;
	@OneToOne
	private Theme theme; // action de formation envisagé
	@OneToMany
	private List<Module> listModules; //contenu souhaité
	@OneToOne
	private Projet projet;
	private boolean valide;
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
	public Projet getProjet() {
		return projet;
	}
	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNbrPrevu() {
		return nbrPrevu;
	}
	public void setNbrPrevu(int nbrPrevu) {
		this.nbrPrevu = nbrPrevu;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public int getPriorite() {
		return priorite;
	}
	public void setPriorite(int priorite) {
		this.priorite = priorite;
	}
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	public List<Module> getListModules() {
		return listModules;
	}
	public void setListModules(List<Module> listModules) {
		this.listModules = listModules;
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
	public boolean isValide() {
		return valide;
	}
	public void setValide(boolean valide) {
		this.valide = valide;
	}
	public Besoin() {
		super();
	}
	public Besoin(int id, BU bu, Date dateDebut, Date dateFin, String type, int nbrPrevu, String recommendation,
			int priorite, Theme theme, List<Module> listModules, Projet projet, boolean valide) {
		super();
		this.id = id;
		this.bu = bu;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.type = type;
		this.nbrPrevu = nbrPrevu;
		this.recommendation = recommendation;
		this.priorite = priorite;
		this.theme = theme;
		this.listModules = listModules;
		this.projet = projet;
		this.valide = valide;
	}
	
	

}
