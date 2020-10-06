package com.fides.supermercato.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.fides.supermercato.model.TipologiaDBO;
import com.fides.supermercato.utilities.HibernateUtil;

@Component
public class TipologiaDAO {

	public void findTipi() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<TipologiaDBO> cquery = cb.createQuery(TipologiaDBO.class);
		Root<TipologiaDBO> root = cquery.from(TipologiaDBO.class);
		cquery.select(cb.construct(TipologiaDBO.class, root.get("tipo"))).distinct(true);
		TypedQuery<TipologiaDBO> query = session.createQuery(cquery);
		List<TipologiaDBO> result = query.getResultList();
		session.close();
		for(TipologiaDBO item: result) {
			System.out.println(item.toStringTipo());
		}
	}

	public List<Integer> findAllTipologie(String tipo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<TipologiaDBO> cquery = cb.createQuery(TipologiaDBO.class);
		Root<TipologiaDBO> root = cquery.from(TipologiaDBO.class);
		cquery.select(root).where(
				cb.equal(root.get("tipo"), tipo)
				);
		TypedQuery<TipologiaDBO> query = session.createQuery(cquery);
		List<TipologiaDBO> result = query.getResultList();
		session.close();
		List<Integer> listaCheServe = new ArrayList<Integer>();
		for (int i = 0; i < result.size(); i++) {
			listaCheServe.add(result.get(i).getId());
		}
		return listaCheServe;
	}
}