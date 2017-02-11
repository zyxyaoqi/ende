package com.ende.domain;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name="meatfood")
public class MeatFood extends BaseFoodEntity implements Serializable {

	private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//种类：牛、绵羊等
	@Column(nullable=false)
	private int species;
	
	//品种
	@Column(nullable=false)
	private int breed;
	
	//类型
	@Column(nullable=false)
	private int type;
	
	@Column(nullable=false)
	private int feedtype;
	
	private int count;
	
	public MeatFood(){}
	
	public MeatFood(int species, int breed, int type, int feedtype) {
		super();
		this.species = species;
		this.breed = breed;
		this.type = type;
		this.feedtype = feedtype;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSpecies() {
		return species;
	}

	public void setSpecies(int species) {
		this.species = species;
	}

	public int getBreed() {
		return breed;
	}

	public void setBreed(int breed) {
		this.breed = breed;
	}

	public int getFeedtype() {
		return feedtype;
	}

	public void setFeedtype(int feedtype) {
		this.feedtype = feedtype;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
