package com.query.dynamic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.query.dynamic.entity.Item;

public interface ItemRepository extends JpaRepository<Item, String>, ItemRepositoryCustom {

}
