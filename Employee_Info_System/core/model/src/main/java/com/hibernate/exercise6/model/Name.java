package com.hibernate.exercise6.model;

import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
public class Name{
	private String firstName,
									lastName,
									middleName,
									suffix,
									title;
	
	public Name() {}
	
	public Name(String title, String firstName, String middleName, String lastName, String suffix){
		this.title = title;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.suffix = suffix;
	}
	
	@Column(name = "TITLE", nullable = true, length = 50)
	public String getTitle(){
		return title;
	} 
	public void setTitle(String title){
		this.title = title;
	}
	
	@Column(name = "FIRST_NAME", nullable = false, length = 250)
	public String getFirstName(){
		return firstName;
	}
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	@Column(name = "MIDDLE_NAME", nullable = true, length = 250)
	public String getMiddleName(){
		return middleName;
	}
	public void setMiddleName(String middleName){
		this.middleName = middleName;
	}
	
	@Column(name = "LAST_NAME", nullable = false, length = 250)
	public String getLastName(){
		return lastName;
	}
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	@Column(name = "SUFFIX", nullable = true, length = 50)
	public String getSuffix(){
		return suffix;
	}
	public void setSuffix(String suffix){
		this.suffix = suffix;
	}
}
