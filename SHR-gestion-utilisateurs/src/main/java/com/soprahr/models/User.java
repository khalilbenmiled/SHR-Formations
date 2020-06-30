package com.soprahr.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String password;
	private String adresse;
	private String tel;
	private boolean isConnected;
	private boolean passwordChanged;
	private boolean deleted;
	@Enumerated(EnumType.STRING)
	private Role role;
	@Enumerated(EnumType.STRING)
	private BU bu;
	private boolean created;
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
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public boolean isConnected() {
		return isConnected;
	}
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public BU getBu() {
		return bu;
	}
	public void setBu(BU bu) {
		this.bu = bu;
	}
	public boolean isPasswordChanged() {
		return passwordChanged;
	}
	public void setPasswordChanged(boolean passwordChanged) {
		this.passwordChanged = passwordChanged;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public boolean isCreated() {
		return created;
	}
	public void setCreated(boolean created) {
		this.created = created;
	}
	public User(int id, String nom, String prenom, String email, String password, String adresse, String tel,
			boolean isConnected, boolean passwordChanged, Role role, BU bu) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.adresse = adresse;
		this.tel = tel;
		this.isConnected = isConnected;
		this.passwordChanged = passwordChanged;
		this.role = role;
		this.bu = bu;
	}
	public User() {
		super();
	}
	
	

}
