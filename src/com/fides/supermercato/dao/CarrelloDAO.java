package com.fides.supermercato.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.fides.supermercato.model.CarrelloDBO;
import com.fides.supermercato.model.ProdottoDBO;
import com.fides.supermercato.utilities.HibernateUtil;

@Component
public class CarrelloDAO {

	//CONTROLLA SE UN CLIENTE HA GIA' UN CARRELLO
	public boolean controllaCarrello(Integer idPersona) throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriabuilder = session.getCriteriaBuilder();
		CriteriaQuery<CarrelloDBO> cquery = criteriabuilder.createQuery(CarrelloDBO.class);
		Root<CarrelloDBO> root = cquery.from(CarrelloDBO.class);
		cquery.select(root).where(
				criteriabuilder.equal(root.get("id_persona"), idPersona)
				);
		TypedQuery<CarrelloDBO> query = session.createQuery(cquery);
		List<CarrelloDBO> result = query.getResultList();
		session.close();
		if (result.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}

	public List<ProdottoDBO> prodotti (int id_carrello) throws SQLException{         
		Session session = HibernateUtil.getSessionFactory().openSession();		
		CriteriaBuilder cb = session.getCriteriaBuilder();	
		CriteriaQuery<ProdottoDBO> cquery = cb.createQuery(ProdottoDBO.class);
		Root<ProdottoDBO> c = cquery.from(ProdottoDBO.class);
		cquery.select(c);
		cquery.where(cb.equal(c.get("id_carrello"), id_carrello));
		Transaction tx =session.beginTransaction();
		TypedQuery<ProdottoDBO> query = session.createQuery(cquery);
		List<ProdottoDBO> result = query.getResultList();
		tx.commit();
		session.close();
		for(ProdottoDBO item: result) {
			System.out.println(item);
		}
		return result;
	} 

	//UPDATE (CHE CARRELLO VUOI USARE?)
	public void update (CarrelloDBO carrello) throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();	
		CriteriaUpdate<CarrelloDBO> update = cb.createCriteriaUpdate(CarrelloDBO.class);
		Root<CarrelloDBO> root = update.from(CarrelloDBO.class);
		update.set("id_persona", carrello.getId_persona());
		update.where(cb.equal(root.get("id"), carrello.getId()));
		Transaction tx =session.beginTransaction();
		session.createQuery(update).executeUpdate();
		tx.commit();
		session.close();
	}

	//UPDATE CarrelloDBO p SET ID_Persona = null WHERE ID_Persona IS NOT NULL
	public void reset() throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();	
		CriteriaUpdate<CarrelloDBO> update = cb.createCriteriaUpdate(CarrelloDBO.class);
		Root<CarrelloDBO> root = update.from(CarrelloDBO.class);
		update.set("id_persona", null);
		update.where(cb.isNotNull(root.get("id_persona")));
		Transaction tx =session.beginTransaction();
		session.createQuery(update).executeUpdate();
		tx.commit();
		session.close();
	}

	//SELECT * FROM S_Carrello  WHERE ID_Persona is null
	public List<CarrelloDBO> findAllVoid() throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();	
		CriteriaQuery<CarrelloDBO> cquery = cb.createQuery(CarrelloDBO.class);
		Root<CarrelloDBO> c = cquery.from(CarrelloDBO.class);
		cquery.select(c);
		cquery.where(cb.isNull(c.get("id_persona")));
		TypedQuery<CarrelloDBO> query = session.createQuery(cquery);
		List<CarrelloDBO> result = query.getResultList();
		session.close();
		return result;
	}

	public List<CarrelloDBO> findAllNotVoid() throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();	
		CriteriaQuery<CarrelloDBO> cquery = cb.createQuery(CarrelloDBO.class);
		Root<CarrelloDBO> c = cquery.from(CarrelloDBO.class);
		cquery.select(c);
		cquery.where(cb.isNotNull(c.get("id_persona")));
		TypedQuery<CarrelloDBO> query = session.createQuery(cquery);
		List<CarrelloDBO> result = query.getResultList();
		session.close();
		return result;
	}
}