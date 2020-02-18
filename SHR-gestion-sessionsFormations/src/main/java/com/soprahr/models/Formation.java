package com.soprahr.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
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
	private Theme theme;
	private int maxParticipants; // nombre de participants MAX
	@OneToMany
	private List<Module> listModules;
	private float duree; // duree ce cette formation
	private float prix;
	private EtatFormation etat;
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
	public List<Module> getListModules() {
		return listModules;
	}
	public void setListModules(List<Module> listModules) {
		this.listModules = listModules;
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
	public Formation(int id, Theme theme, int maxParticipants, List<Module> listModules, float duree, float prix,
			EtatFormation etat) {
		super();
		this.id = id;
		this.theme = theme;
		this.maxParticipants = maxParticipants;
		this.listModules = listModules;
		this.duree = duree;
		this.prix = prix;
		this.etat = etat;
	}
	public Formation() {
		super();
	}
	
	
	

	
}
