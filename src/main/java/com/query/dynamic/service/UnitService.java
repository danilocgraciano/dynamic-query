package com.query.dynamic.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.query.dynamic.entity.Unit;
import com.query.dynamic.exception.InvalidOperationException;
import com.query.dynamic.repository.UnitRepository;

@Service
public class UnitService implements BaseService<Unit, String> {

	@Autowired
	private UnitRepository repository;

	@Override
	public Unit save(Unit e) {
		validate(e);
		return repository.save(e);
	}

	@Override
	public Unit update(String id, Unit e) {
		var unit = findById(id);
		BeanUtils.copyProperties(e, unit);
		return save(unit);
	}

	@Override
	public void delete(String id) {
		repository.delete(findById(id));
	}

	@Override
	@Transactional(readOnly = true)
	public Unit findById(String id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Unit not found " + id, 1));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Unit> findAll(Unit e, Pageable pageable) {
		return repository.find(e, pageable);
	}

	@Override
	public void validate(Unit e) {
		if (e.getCode() == null || e.getCode().isBlank())
			throw new InvalidOperationException("Code is required");

		if (e.getDescription() == null || e.getDescription().isBlank())
			throw new InvalidOperationException("Description is required");
	}

}
