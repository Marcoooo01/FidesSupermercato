package com.fides.supermercato.dao;

import java.sql.SQLException;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.fides.supermercato.model.PersonaDBO;
import com.fides.supermercato.utilities.HibernateUtil;

@Component
public class PersonaDAO {

	//FIND BY ID CUSTOM
	public PersonaDBO findById(Integer idPersona) throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriabuilder = session.getCriteriaBuilder();
		CriteriaQuery<PersonaDBO> cquery = criteriabuilder.createQuery(PersonaDBO.class);
		Root<PersonaDBO> root = cquery.from(PersonaDBO.class);
		cquery.select(root).where(
				criteriabuilder.equal(root.get("id"), idPersona)
				);
		TypedQuery<PersonaDBO> query = session.createQuery(cquery);
		PersonaDBO result = query.getSingleResult();
		session.close();
		return result;
	}

	//UPDATE
	public void update (PersonaDBO persona) throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriabuilder = session.getCriteriaBuilder();
		CriteriaUpdate<PersonaDBO> update = criteriabuilder.createCriteriaUpdate(PersonaDBO.class);
		Root<PersonaDBO> root = update.from(PersonaDBO.class);
		update.set(root.get("id"),persona.getId());
		update.set(root.get("nome"), persona.getNome());
		update.set(root.get("cognome"), persona.getCognome());
		update.set(root.get("email"), persona.getEmail());
		update.set(root.get("telefono"), persona.getTelefono());
		update.set(root.get("id_ruolo"), persona.getId_ruolo());
		update.where(criteriabuilder.equal(root.get("id"),1));
		Transaction  tx = session.beginTransaction();
		session.update(persona);	
		tx.commit();
		session.close();
	}

	//DELETE
	public void delete(PersonaDBO persona) throws SQLException{ 
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriabuilder = session.getCriteriaBuilder();
		CriteriaDelete<PersonaDBO> delete = criteriabuilder.createCriteriaDelete(PersonaDBO.class);
		Root<PersonaDBO> root = delete.from(PersonaDBO.class);
		delete.where(criteriabuilder.equal(root.get("id"), 1));
		Transaction  tx = session.beginTransaction();
		session.delete(persona);
		tx.commit();
		session.close();
	}
}