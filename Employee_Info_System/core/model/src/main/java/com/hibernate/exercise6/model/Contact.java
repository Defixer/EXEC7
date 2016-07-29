package com.hibernate.exercise6.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.Embedded;

@Entity
@Table(name = "CONTACT", uniqueConstraints = {
	@UniqueConstraint(columnNames = "CONTACT_TYPE"),
	@UniqueConstraint(columnNames = "CONTACT_DETAILS")
})
public class Contact extends UniqueId{
	private int id;

	private String contactDetails;
	private String contactType;
	
	public Contact(){}
	
	public Contact(String contactDetails, String contactType){
		this.contactDetails = contactDetails;
		this.contactType = contactType;
	}
	
	@Column(name = "CONTACT_DETAILS", nullable = false, length = 250)
	public String getContactDetails(){
		return contactDetails;
	}
	public void setContactDetails(String contactDetails){
		this.contactDetails = contactDetails;
	}
	
	@Column(name = "CONTACT_TYPE", nullable = false, length = 250)
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
		
		Contact obj2 = (Contact)obj;
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
