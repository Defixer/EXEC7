package com.hibernate.exercise6.model;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.GenerationType;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class UniqueId{	
	private int uniqueId;
	
	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "id", nullable = false, updatable = false)
	public int getId(){
		return uniqueId;
	}	
	public void setId(int uniqueId){
		this.uniqueId = uniqueId;
	}
}
