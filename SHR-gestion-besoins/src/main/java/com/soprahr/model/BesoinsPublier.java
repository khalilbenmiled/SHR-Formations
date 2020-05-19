package com.soprahr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class BesoinsPublier implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	private int idManager;
	private String theme;
	private int quarter;
	private boolean publier;
	@OneToMany
	private List<Besoins> listBesoins = new ArrayList<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Besoins> getListBesoins() {
		return listBesoins;
	}
	public void setListBesoins(List<Besoins> listBesoins) {
		this.listBesoins = listBesoins;
	}
	
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}

	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public boolean isPublier() {
		return publier;
	}
	public void setPublier(boolean publier) {
		this.publier = publier;
	}
	
	public BesoinsPublier(int idManager, String theme, int quarter, boolean publier, List<Besoins> listBesoins) {
		super();
		this.idManager = idManager;
		this.theme = theme;
		this.quarter = quarter;
		this.publier = publier;
		this.listBesoins = listBesoins;
	}
	public int getIdManager() {
		return idManager;
	}
	public void setIdManager(int idManager) {
		this.idManager = idManager;
	}
	public BesoinsPublier() {
		super();
	}
	
	
	
	

}
