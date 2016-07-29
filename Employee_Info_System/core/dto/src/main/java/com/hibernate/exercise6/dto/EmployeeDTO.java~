package com.hibernate.exercise6.dto;

import java.util.Set;
import java.util.Comparator;

public class EmployeeDTO extends UniqueIdDTO{	
	private NameDTO nameInfo;
	private AddressDTO addressInfo;
	private OtherInfoDTO otherInfo;
	private Set<ContactDTO> contact;
	private Set<RoleDTO> role;
	
	public EmployeeDTO() {}
	
	public EmployeeDTO(NameDTO nameInfo, AddressDTO addressInfo, OtherInfoDTO otherInfo){
		this.nameInfo = nameInfo;
		this.addressInfo = addressInfo;
		this.otherInfo = otherInfo;
	}
	
	public NameDTO getName(){
		return nameInfo;
	}
	public void setName(NameDTO nameInfo){
		this.nameInfo = nameInfo;
	}
	
	public AddressDTO getAddress(){
		return addressInfo;
	}
	public void setAddress(AddressDTO addressInfo){
		this.addressInfo = addressInfo;
	}
	
	public OtherInfoDTO getOtherInfo(){
		return otherInfo;
	}
	public void setOtherInfo(OtherInfoDTO otherInfo){
		this.otherInfo = otherInfo;
	}
	
	public Set<ContactDTO> getContacts(){
		return contact;
	}
	public void setContacts(Set<ContactDTO> contact){
		this.contact = contact;
	}
	
	public Set<RoleDTO> getRole(){
		return role;
	}
	public void setRole(Set<RoleDTO> role){
		this.role = role;
	}
	
	public static Comparator<EmployeeDTO> employeeGwaComparator = new Comparator<EmployeeDTO>(){
		public int compare(EmployeeDTO emp1, EmployeeDTO emp2){
			String emp1Gwa = String.valueOf(emp1.otherInfo.getGwa());
			String emp2Gwa = String.valueOf(emp2.otherInfo.getGwa());
			
			return emp1Gwa.compareTo(emp2Gwa);
		}		
	};
}
