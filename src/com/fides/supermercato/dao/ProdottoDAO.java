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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fides.supermercato.model.ProdottoDBO;
import com.fides.supermercato.utilities.HibernateUtil;

@Component
public class ProdottoDAO {

	@Autowired
	TipologiaDAO tipologia;

	public List<ProdottoDBO> findByTipoInCarrello(Integer carrello, String tipo) throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ProdottoDBO> cquery = cb.createQuery(ProdottoDBO.class);
		Root<ProdottoDBO> root = cquery.from(ProdottoDBO.class);
		List<Integer> listaT = tipologia.findAllTipologie(tipo);
		cquery.select(root).where(
				root.get("id_tipologia").in(listaT),
				cb.equal(root.get("id_carrello"), carrello)
				);
		TypedQuery<ProdottoDBO> query = session.createQuery(cquery);
		List<ProdottoDBO> result = query.getResultList();
		session.close();
		return result;
	}

	public List<ProdottoDBO> findByTipoInScaffale(String tipo) throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ProdottoDBO> cquery = cb.createQuery(ProdottoDBO.class);
		Root<ProdottoDBO> root = cquery.from(ProdottoDBO.class);
		List<Integer> listaT = tipologia.findAllTipologie(tipo);
		cquery.select(root).where(
				root.get("id_tipologia").in(listaT),
				cb.isNull(root.get("id_carrello"))
				);
		TypedQuery<ProdottoDBO> query = session.createQuery(cquery);
		List<ProdottoDBO> result = query.getResultList();
		session.close();
		for(ProdottoDBO item: result) {
			System.out.println(item);
		}
		return result;
	}

	//SELECT (TROVA I PRODOTTI DISPONIBILI SU SCAFFALE)

	public List<ProdottoDBO> findProdottiDisp() throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ProdottoDBO> cquery = cb.createQuery(ProdottoDBO.class);
		Root<ProdottoDBO> root = cquery.from(ProdottoDBO.class);
		cquery.select(root).where(
				cb.isNull(root.get("id_carrello"))
				);
		TypedQuery<ProdottoDBO> query = session.createQuery(cquery);
		List<ProdottoDBO> result = query.getResultList();
		session.close();
		for(ProdottoDBO item: result) {
			System.out.println(item.toStringDisp());
		}
		return result;
	}

	//BISOGNEREBBE CONTROLLARE CHE IL CARRELLO ESISTA PER DAVVERO  
	//RITORNA TUTTI I PRODOTTI IN UN CARRELLO INSERITO IN INPUT

	public List<ProdottoDBO> findProdottiInCarrello(int carrello) throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ProdottoDBO> cquery = cb.createQuery(ProdottoDBO.class);
		Root<ProdottoDBO> root = cquery.from(ProdottoDBO.class);
		cquery.select(root).where(
				cb.equal(root.get("id_carrello"), carrello)
				);
		TypedQuery<ProdottoDBO> query = session.createQuery(cquery);
		List<ProdottoDBO> result = query.getResultList();
		session.close();
		for(ProdottoDBO item: result) {
			System.out.println(item);
		}
		return result;
	}

	//AGGIORNA IL RECORD DELLA TABELLA PRODOTTO SETTANDO L'ID DEL CARRELLO = A QUELLO PASSATO IN INPUT, 
	//DOVE IL PRODOTTO E' UGUALE A QUELLO PASSATO COME PARAMETRO

	public void inserisciProdottoInCarrello (int prodotto, Integer carrello) throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaUpdate<ProdottoDBO> cquery = cb.createCriteriaUpdate(ProdottoDBO.class);
		Root<ProdottoDBO> root = cquery.from(ProdottoDBO.class);
		cquery.set("id_carrello", carrello).where(
				cb.equal(root.get("id"), prodotto)
				);
		session.createQuery(cquery).executeUpdate();
		tx.commit();
		session.close();
	}

	//AGGIORNA IL RECORD DELLA TABELLA PRODOTTO SETTANDO L'ID DEL CARRELLO A NULL, 
	//DOVE IL PRODOTTO E' UGUALE A QUELLO PASSATO COME PARAMETRO

	public void togliProdottoDaCarrello (int prodotto) throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaUpdate<ProdottoDBO> cquery = cb.createCriteriaUpdate(ProdottoDBO.class);
		Root<ProdottoDBO> root = cquery.from(ProdottoDBO.class);
		cquery.set("id_carrello", null).where(
				cb.equal(root.get("id"), prodotto)
				);
		session.createQuery(cquery).executeUpdate();
		tx.commit();
		session.close();
	}

	//METODO CHE RESETTA LA TABELLA PRODOTTO SETTANDO TUTTI GLI ID_Carrello = NULL

	public void reset() throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaUpdate<ProdottoDBO> cquery = cb.createCriteriaUpdate(ProdottoDBO.class);
		Root<ProdottoDBO> root = cquery.from(ProdottoDBO.class);
		cquery.set("id_carrello", null).where(
				cb.isNotNull(root.get("id_carrello"))
				);
		session.createQuery(cquery).executeUpdate();
		tx.commit();
		session.close();
	}
}