package com.ende.form;

import java.io.Serializable;


public class MeatFoodCriteria extends FoodCriteria implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String species;
	
	private String breed;
	
	private String type;
	
	private String feedtype;
	
    private int count;
    
    public MeatFoodCriteria(){}
	

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}


	public String getBreed() {
		return breed;
	}


	public void setBreed(String breed) {
		this.breed = breed;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getFeedtype() {
		return feedtype;
	}


	public void setFeedtype(String feedtype) {
		this.feedtype = feedtype;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}

  
}
