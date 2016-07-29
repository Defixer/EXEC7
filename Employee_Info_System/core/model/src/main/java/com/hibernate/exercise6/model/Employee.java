package com.hibernate.exercise6.model;

import java.util.Set;
import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "EMPLOYEE")
public class Employee extends UniqueId{	
	@Embedded
	private Name nameInfo;
	
	@Embedded
	private Address addressInfo;
	
	@Embedded
	private OtherInfo otherInfo;
		
	private Set<Contact> contact;
	private Set<Role> role;
	
	public Employee() {}
	
	public Employee(Name nameInfo, Address addressInfo, OtherInfo otherInfo){
		this.nameInfo = nameInfo;
		this.addressInfo = addressInfo;
		this.otherInfo = otherInfo;
	}
	
	public Name getName(){
		return nameInfo;
	}
	public void setName(Name nameInfo){
		this.nameInfo = nameInfo;
	}
	
	public Address getAddress(){
		return addressInfo;
	}
	public void setAddress(Address addressInfo){
		this.addressInfo = addressInfo;
	}
	
	public OtherInfo getOtherInfo(){
		return otherInfo;
	}
	public void setOtherInfo(OtherInfo otherInfo){
		this.otherInfo = otherInfo;
	}
	
	@OneToMany(fetch = FetchType.LAZY)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "EMPLOYEE_ID")
	public Set<Contact> getContacts(){
		return contact;
	}
	public void setContacts(Set<Contact> contact){
		this.contact = contact;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinTable(name = "EMPLOYEE_ROLE", joinColumns = {
		@JoinColumn(name = "EMPLOYEE_ID", nullable = false, updatable = false)},
		inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false, updatable = false)}
	)
	public Set<Role> getRole(){
		return role;
	}
	public void setRole(Set<Role> role){
		this.role = role;
	}
	
	public static Comparator<Employee> employeeGwaComparator = new Comparator<Employee>(){
		public int compare(Employee emp1, Employee emp2){
			String emp1Gwa = String.valueOf(emp1.otherInfo.getGwa());
			String emp2Gwa = String.valueOf(emp2.otherInfo.getGwa());
			
			return emp1Gwa.compareTo(emp2Gwa);
		}		
	};
}
