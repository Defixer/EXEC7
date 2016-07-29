package com.hibernate.exercise6.dto;

public abstract class UniqueIdDTO{	
	private int uniqueIdDTO;
	
	public int getId(){
		return uniqueIdDTO;
	}	
	public void setId(int uniqueIdDTO){
		this.uniqueIdDTO = uniqueIdDTO;
	}
}
