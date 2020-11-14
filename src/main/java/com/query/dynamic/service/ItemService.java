package com.query.dynamic.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.query.dynamic.entity.Item;
import com.query.dynamic.exception.InvalidOperationException;
import com.query.dynamic.repository.ItemRepository;

@Service
public class ItemService implements BaseService<Item, String> {

	@Autowired
	private ItemRepository repository;

	@Override
	public Item save(Item e) {
		validate(e);
		return repository.save(e);
	}

	@Override
	public Item update(String id, Item e) {
		var item = findById(id);
		BeanUtils.copyProperties(e, item);
		return save(item);
	}

	@Override
	public void delete(String id) {
		repository.delete(findById(id));

	}

	@Override
	@Transactional(readOnly = true)
	public Item findById(String id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Item not found " + id, 1));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Item> findAll(Item e, Pageable pageable) {
		return repository.find(e, pageable);
	}

	@Override
	public void validate(Item e) {
		if (e.getSku() == null || e.getSku().isBlank())
			throw new InvalidOperationException("SKU is required");

		if (e.getName() == null || e.getName().isBlank())
			throw new InvalidOperationException("Name is required");

		if (e.getUnit() == null)
			throw new InvalidOperationException("Unit is required");

		if (e.getUnitPrice() == null || e.getUnitPrice().doubleValue() == 0)
			throw new InvalidOperationException("Unit price needs to be greather than zero");

	}

}
