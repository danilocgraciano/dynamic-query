package com.query.dynamic.builder;

import com.query.dynamic.entity.Item;
import com.query.dynamic.entity.Unit;

public class ItemBuilder {

	private String sku;

	private String name;

	private Double unitPrice;

	private Unit unit;

	public ItemBuilder withDefaults() {
		this.sku = "001";
		this.name = "name";
		this.unitPrice = 1.99d;
		return this;
	}

	public Item build() {
		var item = new Item();
		item.setName(this.name);
		item.setSku(this.sku);
		item.setUnit(this.unit);
		item.setUnitPrice(this.unitPrice);
		return item;
	}
}
