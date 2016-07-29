package com.hibernate.exercise6.dto;

public class AddressDTO{
	private String streetNo,
									brgy,
									cityMunicipality,
									zipcode;
									
	public AddressDTO() {}
	
	public AddressDTO(String streetNo, String brgy, String cityMunicipality, String zipcode){
		this.streetNo = streetNo;
		this.brgy = brgy;
		this.cityMunicipality = cityMunicipality;
		this.zipcode = zipcode;
	}
	
	public String getStreetNo(){
		return streetNo;
	}
	public void setStreetNo(String streetNo){
		this.streetNo = streetNo;
	}
	
	public String getBrgy(){
		return brgy;
	}
	public void setBrgy(String brgy){
		this.brgy = brgy;
	}
	
	public String getCityMunicipality(){
		return cityMunicipality;
	}
	public void setCityMunicipality(String cityMunicipality){
		this.cityMunicipality = cityMunicipality;
	}
	
	public String getZipcode(){
		return zipcode;
	}
	public void setZipcode(String zipcode){
		this.zipcode = zipcode;
	}
}
