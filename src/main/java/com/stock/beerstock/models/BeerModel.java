package com.stock.beerstock.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


@Entity(name = "Beer")
public class BeerModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Is mandatory.")
	@NotNull(message = "Is mandatory.")
	@Length(min = 3, max = 100, message = "Must be between 3 and 100 characters.")
	@Column(unique = true, nullable = false, length = 100)
	private String name;
	
	@NotBlank(message = "Is mandatory.")
	@NotNull(message = "Is mandatory.")
	@Length(min = 3, max = 100, message = "Must be between 3 and 100 characters.")
	@Column(nullable = false, length = 100)
	private String brand;
	
	@NotNull(message = "Is mandatory.")
	@Min(1)
	@Max(value = 100, message="Is too large")
	@Column(nullable = false)
	private int quantity;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
