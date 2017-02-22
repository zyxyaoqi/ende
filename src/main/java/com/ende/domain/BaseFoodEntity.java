package com.ende.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseFoodEntity extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(nullable=true)
	protected String contactor;
	
	@Column(nullable=true)
	protected String tel;
	
	@Column(nullable=true)
	protected String address;
	
	@Column(nullable=true)
	protected double lng;
	
	@Column(nullable=true)
	protected double lat;
	
	@Column(nullable=true)
	protected String note;
	
	@Column(nullable=true)
	protected String photolink;

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPhotolink() {
		return photolink;
	}

	public void setPhotolink(String photolink) {
		this.photolink = photolink;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	
}
