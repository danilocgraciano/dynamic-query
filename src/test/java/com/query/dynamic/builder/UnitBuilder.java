package com.query.dynamic.builder;

import com.query.dynamic.entity.Unit;

public class UnitBuilder {

	private String id;

	private String description;

	public UnitBuilder withDefaults() {
		this.id = "001";
		this.description = "description";
		return this;
	}

	public Unit build() {
		var unit = new Unit();
		unit.setId(this.id);
		unit.setDescription(this.description);
		return unit;
	}

}
