package com.fides.supermercato.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "S_Tipologia")
@Component
public class TipologiaDBO {
	@Id
	@Column(name = "ID")
	int id;

	@Column(name = "Nome")
	String nome;

	@Column(name = "Tipo")
	String tipo;
	
	public TipologiaDBO() {}

	public TipologiaDBO(String tipo) {this.tipo = tipo;}

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "TipologiaDBO [id=" + id + ", nome=" + nome + ", tipo=" + tipo + "]";
	}

	public String toStringTipo() {
		return "TipologiaDBO [tipo=" + tipo + "]";
	}
}