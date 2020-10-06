package com.fides.supermercato.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "S_Carrello")
@Component
public class CarrelloDBO {
	@Id
	@Column(name = "ID")
	int id;

	@JoinColumn(name="ID_PERSONA", table = "S_PERSONA", referencedColumnName = "ID")
	Integer id_persona;

	public int getId() {
		return id;
	} 

	public void setId(int id) {
		this.id = id;
	}

	public Integer getId_persona() {
		return id_persona;
	}

	public void setId_persona(Integer id_persona) {
		if (id_persona == 0) {
			this.id_persona = null;
		}else {
			this.id_persona = id_persona;
		}
	}

	@Override
	public String toString() {
		return "CarrelloDBO [id=" + id + ", id_persona=" + id_persona + "]";
	}
}