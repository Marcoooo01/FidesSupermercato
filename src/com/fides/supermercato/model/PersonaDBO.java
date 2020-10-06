package com.fides.supermercato.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "S_Persona")
@Component
public class PersonaDBO {
	@Id
	@Column(name = "ID")
	int id;

	@Column(name = "Nome")
	String nome;

	@Column(name = "Cognome")
	String cognome;

	@Column(name = "Email")
	String email;

	@Column(name = "Telefono")
	String telefono;

	@JoinColumn(name = "ID_Ruolo", table = "S_Ruolo", referencedColumnName = "ID")
	Integer id_ruolo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Integer getId_ruolo() {
		return id_ruolo;
	}

	public void setId_ruolo(Integer id_ruolo) {
		this.id_ruolo = id_ruolo;
	}

	@Override
	public String toString() {
		return "PersonaDBO [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", telefono="
				+ telefono + ", id_ruolo=" + id_ruolo + "]";
	}
}