package com.ende.form;

import java.io.Serializable;

public class MeatFoodCriteria implements Serializable {
 
	private static final long serialVersionUID = 1L;

	private String species;
	
	private String breed;
	
	private String type;
	
	private String feedtype;
	
    private String other;
    
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


	public String getOther() {
		return other;
	}


	public void setOther(String other) {
		this.other = other;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	 

}
