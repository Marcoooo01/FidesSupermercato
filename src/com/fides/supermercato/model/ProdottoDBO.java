package com.fides.supermercato.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "S_Prodotto")
@Component
public class ProdottoDBO {
	@Id
	@Column(name = "ID")
	int id;

	@Column(name = "Nome")
	String nome;

	@Column(name = "Prezzo")
	float prezzo;

	@Column(name = "ID_Carrello")
	Integer id_carrello;

	@Column(name = "ID_Tipologia")
	Integer id_tipologia;

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

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public Integer getId_carrello() {
		return id_carrello;
	}

	public void setId_carrello(Integer id_carrello) {
		if (id_carrello == 0) {
			this.id_carrello = null;
		}else {
			this.id_carrello = id_carrello;
		}
	}

	public Integer getId_tipologia() {
		return id_tipologia;
	}

	public void setId_tipologia(Integer id_tipologia) {
		this.id_tipologia = id_tipologia;
	}

	@Override
	public String toString() {
		return "ProdottoDBO [id=" + id + ", nome=" + nome + ", prezzo=" + prezzo + ", id_carrello=" + id_carrello
				+ ", id_tipologia=" + id_tipologia + "]";
	}

	public String toStringDisp() {
		return "ProdottoDBO [id=" + id + ", nome=" + nome + ", prezzo=" + prezzo + "]";
	}
}