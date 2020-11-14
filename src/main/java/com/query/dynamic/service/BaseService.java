package com.query.dynamic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<E, ID> {

	E save(E e);

	E update(ID id, E e);

	void delete(ID id);

	E findById(ID id);

	Page<E> findAll(E e, Pageable pageable);

	void validate(E e);

}
