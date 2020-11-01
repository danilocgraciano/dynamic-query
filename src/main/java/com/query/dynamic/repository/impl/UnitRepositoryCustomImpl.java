package com.query.dynamic.repository.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

import com.query.dynamic.entity.Unit;
import com.query.dynamic.repository.AbstractRepository;
import com.query.dynamic.repository.UnitRepositoryCustom;

@SuppressWarnings("unchecked")
public class UnitRepositoryCustomImpl extends AbstractRepository implements UnitRepositoryCustom {

	public UnitRepositoryCustomImpl() {
		super(Unit.class);
	}

	@Override
	public Page<Unit> find(Unit e, Pageable pageable) {
		String query = "from Unit unit ";
		Object args[] = null;

		if (e != null) {
			int param = 0;
			String and = "";
			String where = "where ";
			List<Object> params = new ArrayList<>();

			if (e.getDescription() != null && !e.getDescription().trim().isEmpty()) {
				query += where + and + "lower(unit.description) like lower(concat(?" + (param++) + ",'%'))";
				params.add(e.getDescription());
				and = " and ";
				where = "";
			}

			args = params.toArray();
		}

		String countQuery = "Select count(unit.id) " + query;
		query += " order by ";
		if (pageable == null || pageable.getSort().isEmpty()) {
			query += "unit.description ASC";
		} else {
			Iterator<Order> it = pageable.getSort().iterator();
			while (it.hasNext()) {
				Order order = it.next();
				query += "unit.";
				query += order.getProperty();
				query += " ";
				query += order.getDirection();
				if (it.hasNext())
					query += ", ";
			}
		}
		return find(query, countQuery, pageable, args);

	}

}
