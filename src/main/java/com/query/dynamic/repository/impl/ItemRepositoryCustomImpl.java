package com.query.dynamic.repository.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

import com.query.dynamic.entity.Item;
import com.query.dynamic.repository.AbstractRepository;
import com.query.dynamic.repository.ItemRepositoryCustom;

@SuppressWarnings("unchecked")
public class ItemRepositoryCustomImpl extends AbstractRepository implements ItemRepositoryCustom {

	public ItemRepositoryCustomImpl() {
		super(Item.class);
	}

	@Override
	public Page<Item> find(Item e, Pageable pageable) {
		String query = "from Item item ";
		Object args[] = null;

		if (e != null) {
			int param = 0;
			String and = "";
			String where = "where ";
			List<Object> params = new ArrayList<>();

			if (e.getName() != null && !e.getName().trim().isEmpty()) {
				query += where + and + "lower(item.name) like lower(concat(?" + (param++) + ",'%'))";
				params.add(e.getName());
				and = " and ";
				where = "";
			}

			if (e.getUnit() != null && e.getUnit().getCode() != null) {
				query += where + and + "item.unit.id = ?" + (param++);
				params.add(e.getUnit().getCode());
				and = " and ";
				where = "";
			}

			args = params.toArray();
		}

		String countQuery = "Select count(item.id) " + query;
		query += " order by ";
		if (pageable == null || pageable.getSort().isEmpty()) {
			query += "item.name ASC";
		} else {
			Iterator<Order> it = pageable.getSort().iterator();
			while (it.hasNext()) {
				Order order = it.next();
				query += "item.";
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
