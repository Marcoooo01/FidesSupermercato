package com.fides.supermercato.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "S_Ruolo")
@Component
public class RuoloDBO {
	@Id
	@Column(name = "ID")
	int id;

	@Column(name = "Nome")
	String nome;

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

	@Override
	public String toString() {
		return "RuoloDBO [id=" + id + ", nome=" + nome + "]";
	}
}