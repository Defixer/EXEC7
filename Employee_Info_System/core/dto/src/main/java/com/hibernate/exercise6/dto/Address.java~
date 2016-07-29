package com.hibernate.exercise6.model;

import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
public class Address{
	private String streetNo,
									brgy,
									cityMunicipality,
									zipcode;
									
	public Address() {}
	
	public Address(String streetNo, String brgy, String cityMunicipality, String zipcode){
		this.streetNo = streetNo;
		this.brgy = brgy;
		this.cityMunicipality = cityMunicipality;
		this.zipcode = zipcode;
	}
	
	@Column(name = "STREET_NO", nullable = false, length = 50)
	public String getStreetNo(){
		return streetNo;
	}
	public void setStreetNo(String streetNo){
		this.streetNo = streetNo;
	}
	
	@Column(name = "BRGY", nullable = false, length = 50)
	public String getBrgy(){
		return brgy;
	}
	public void setBrgy(String brgy){
		this.brgy = brgy;
	}
	
	@Column(name = "CITY_MUNICIPALITY", nullable = false, length = 250)
	public String getCityMunicipality(){
		return cityMunicipality;
	}
	public void setCityMunicipality(String cityMunicipality){
		this.cityMunicipality = cityMunicipality;
	}
	
	@Column(name = "ZIPCODE", nullable = false, length = 50)
	public String getZipcode(){
		return zipcode;
	}
	public void setZipcode(String zipcode){
		this.zipcode = zipcode;
	}
}
