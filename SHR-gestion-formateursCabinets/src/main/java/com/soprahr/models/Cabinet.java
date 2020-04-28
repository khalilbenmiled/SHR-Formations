package com.soprahr.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Cabinet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private String nom;
	private String email;
	private String tel;
	private String contact;
	@Enumerated(EnumType.STRING)
	private TypeFormation typeFormation;
	@ManyToMany
	private List<Domaine> listDomaines = new ArrayList<>();
	@OneToMany
	private List<Formateur> listFormateurs = new ArrayList<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public TypeFormation getTypeFormation() {
		return typeFormation;
	}
	public void setTypeFormation(TypeFormation typeFormation) {
		this.typeFormation = typeFormation;
	}

	public List<Domaine> getListDomaines() {
		return listDomaines;
	}
	public void setListDomaines(List<Domaine> listDomaines) {
		this.listDomaines = listDomaines;
	}

	public List<Formateur> getListFormateurs() {
		return listFormateurs;
	}
	public void setListFormateurs(List<Formateur> listFormateurs) {
		this.listFormateurs = listFormateurs;
	}

	public Cabinet(int id, String nom, String email, String tel, String contact, TypeFormation typeFormation,
			List<Domaine> listDomaines, List<Formateur> listFormateurs) {
		super();
		this.id = id;
		this.nom = nom;
		this.email = email;
		this.tel = tel;
		this.contact = contact;
		this.typeFormation = typeFormation;
		this.listDomaines = listDomaines;
		this.listFormateurs = listFormateurs;
	}
	public Cabinet() {
		super();
	}
	
	
	
	

}
