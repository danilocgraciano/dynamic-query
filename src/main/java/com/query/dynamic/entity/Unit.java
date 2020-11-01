package com.query.dynamic.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "unit")
@Getter
@Setter
public class Unit {

	@Id
	private String id;

	@NotBlank(message = "Description is required")
	private String description;

}
