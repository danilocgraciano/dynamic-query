package com.query.dynamic.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AbstractRepository {

	@PersistenceContext
	protected EntityManager entityManager;

	private Class clazz;

	public AbstractRepository(Class clazz) {
		this.clazz = clazz;
	}

	public Page find(String query, String countQuery, Pageable pageable, Object... args) {

		Long count = getCount(countQuery, args);

		TypedQuery tQuery = entityManager.createQuery(query, this.clazz);

		setParameters(tQuery, args);

		setPageParams(pageable, tQuery);

		if (pageable == null)
			return new PageImpl(tQuery.getResultList());
		else
			return new PageImpl(tQuery.getResultList(), pageable, count);
	}

	private Long getCount(String countQuery, Object... args) {

		TypedQuery<Long> tQuery = entityManager.createQuery(countQuery, Long.class);

		setParameters(tQuery, args);

		return tQuery.getResultList().get(0);
	}

	private void setPageParams(Pageable pageable, TypedQuery tQuery) {
		if (pageable != null) {
			tQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
			tQuery.setMaxResults(pageable.getPageSize());
		}
	}

	private void setParameters(TypedQuery tQuery, Object... args) {
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				tQuery.setParameter(i, args[i]);
			}
		}
	}

}
