package com.query.dynamic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.query.dynamic.entity.Unit;

public interface UnitRepositoryCustom {

	Page<Unit> find(Unit e, Pageable pageable);

}
