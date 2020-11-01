package com.query.dynamic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.query.dynamic.entity.Unit;

public interface UnitRepository extends JpaRepository<Unit, String>, UnitRepositoryCustom {

}
