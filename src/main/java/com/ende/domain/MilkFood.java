package com.ende.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="milkfood")
public class MilkFood extends BaseFoodEntity implements Serializable {

	private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private int type;

	@Column(nullable=false)
	private int sourcetype;
	
	@Column(nullable=false)
	private double price;
	
	private double count;
	
	
	public MilkFood(){}

	public MilkFood(int sourcetype, double price, String contactor, String tel) {
		super();
		this.sourcetype = sourcetype;
		this.price = price;
		this.contactor = contactor;
		this.tel = tel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSourcetype() {
		return sourcetype;
	}

	public void setSourcetype(int sourcetype) {
		this.sourcetype = sourcetype;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
