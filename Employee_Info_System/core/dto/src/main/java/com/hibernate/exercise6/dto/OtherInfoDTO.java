package com.hibernate.exercise6.dto;

import java.util.Date;

public class OtherInfoDTO{
	private Date birthday,
								hireDate;
	private double gwa;
	private boolean isEmployed;
	
	public OtherInfoDTO() {}
	
	public OtherInfoDTO(Date birthday, Date hireDate, double gwa, boolean isEmployed){
		this.birthday = birthday;
		this.hireDate = hireDate;
		this.gwa = gwa;
		this.isEmployed = isEmployed;
	}
	
	public Date getBirthday(){
		return birthday;
	}
	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}
	
	public Date getHireDate(){
		return hireDate;
	}
	public void setHireDate(Date hireDate){
		this.hireDate = hireDate;
	}
	
	public double getGwa(){
		return gwa;
	}
	public void setGwa(double gwa){
		this.gwa = gwa;
	}
	
	public boolean getIsEmployed(){
		return isEmployed;
	}
	public void setIsEmployed(boolean isEmployed){
		this.isEmployed = isEmployed;
	}	
}
