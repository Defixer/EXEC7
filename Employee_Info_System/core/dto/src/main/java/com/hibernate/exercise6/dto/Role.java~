package com.hibernate.exercise6.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.Embedded;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ROLE", uniqueConstraints = {
	@UniqueConstraint(columnNames = "ROLE_NAME")
})
public class Role extends UniqueId{
	private int id;

	private String roleName;
	private Set<Employee> employees;
	
	public Role() {}
	
	public Role(String roleName){
		this.roleName = roleName;
	}
	
	@Column(name = "ROLE_NAME", nullable = false, length = 250)
	public String getRoleName(){
		return roleName;
	}
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<Employee> getEmployee(){
		return employees;
	}
	public void setEmployee(Set<Employee> employees){
		this.employees = employees;
	}
	
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!this.getClass().equals(obj.getClass())){
			return false;
		}
		
		Role obj2 = (Role)obj;
		if((this.id == obj2.getId()) && (this.roleName.equals(obj2.getRoleName()))){
			return true;
		}
		return false;
	}
	
	public int hashCode(){
		int tmp = 0;
		tmp = (id + roleName).hashCode();
		return tmp;
	}
	
}
