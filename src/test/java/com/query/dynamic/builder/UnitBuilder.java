package com.query.dynamic.builder;

import com.query.dynamic.entity.Unit;

public class UnitBuilder {

	private String code;

	private String description;

	public UnitBuilder withDefaults() {
		this.code = "001";
		this.description = "description";
		return this;
	}

	public Unit build() {
		var unit = new Unit();
		unit.setCode(this.code);
		unit.setDescription(this.description);
		return unit;
	}

}
