package com.query.dynamic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.query.dynamic.entity.Item;

public interface ItemRepositoryCustom {

	Page<Item> find(Item e, Pageable pageable);

}
