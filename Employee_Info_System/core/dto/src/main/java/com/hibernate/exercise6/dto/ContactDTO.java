package com.hibernate.exercise6.dto;

public class ContactDTO extends UniqueIdDTO{
	private int id;
	private String contactDetails;
	private String contactType;
	
	public ContactDTO(){}
	
	public ContactDTO(String contactDetails, String contactType){
		this.contactDetails = contactDetails;
		this.contactType = contactType;
	}
	
	public String getContactDetails(){
		return contactDetails;
	}
	public void setContactDetails(String contactDetails){
		this.contactDetails = contactDetails;
	}
	
	public String getContactType(){
		return contactType;
	}
	public void setContactType(String contactType){
		this.contactType = contactType;
	}
	
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!this.getClass().equals(obj.getClass())){
			return false;
		}
		
		ContactDTO obj2 = (ContactDTO)obj;
		if((this.id == obj2.getId()) && (this.contactDetails.equals(obj2.getContactDetails()))){
			return true;
		}
		return false;
	}
	
	public int hashCode(){
		int tmp = 0;
		tmp = (id + contactDetails).hashCode();
		return tmp;
	}
}
