package com.query.dynamic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item")
@Getter
@Setter
public class Item {

	@Id
	private String sku;

	@NotBlank(message = "Name is required")
	private String name;

	@Column(name = "unit_price")
	@NotNull(message = "Unit Price is required")
	private Double unitPrice;

	@ManyToOne
	@JoinColumn(name = "unit_id")
	private Unit unit;

}
