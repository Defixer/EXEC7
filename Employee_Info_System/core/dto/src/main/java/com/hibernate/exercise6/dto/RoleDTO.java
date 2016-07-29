package com.hibernate.exercise6.dto;

import java.util.Set;

public class RoleDTO extends UniqueIdDTO{
	private int id;
	private String roleName;
	private Set<EmployeeDTO> employees;
	
	public RoleDTO() {}
	
	public RoleDTO(String roleName){
		this.roleName = roleName;
	}
	
	public String getRoleName(){
		return roleName;
	}
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
	
	public Set<EmployeeDTO> getEmployee(){
		return employees;
	}
	public void setEmployee(Set<EmployeeDTO> employees){
		this.employees = employees;
	}
	
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!this.getClass().equals(obj.getClass())){
			return false;
		}
		
		RoleDTO obj2 = (RoleDTO)obj;
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
