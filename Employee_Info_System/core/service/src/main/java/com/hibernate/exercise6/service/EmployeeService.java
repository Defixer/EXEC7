package com.hibernate.exercise6.service;

import com.hibernate.exercise6.dao.PersonDAO;
import com.hibernate.exercise6.dao.RoleDAO;
import com.hibernate.exercise6.model.Employee;
import com.hibernate.exercise6.model.Name;
import com.hibernate.exercise6.model.Address;
import com.hibernate.exercise6.model.OtherInfo;
import com.hibernate.exercise6.model.Contact;
import com.hibernate.exercise6.model.Role;
import com.hibernate.exercise6.utilities.HibernateUtil;

import com.hibernate.exercise6.dto.EmployeeDTO;
import com.hibernate.exercise6.dto.NameDTO;
import com.hibernate.exercise6.dto.AddressDTO;
import com.hibernate.exercise6.dto.OtherInfoDTO;
import com.hibernate.exercise6.dto.RoleDTO;
import com.hibernate.exercise6.dto.ContactDTO;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.MessageFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.NoSuchElementException;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
import org.hibernate.Criteria;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmployeeService{
	PersonDAO personDAO = new PersonDAO();
	private final String BY_NAME = "by name",
												BY_GWA = "by gwa",
												BY_HIRE_DATE = "by hire date",
												DATE_FLAG = "y";
	private HibernateUtil hibernateUtil = new HibernateUtil();
	
	public void createEmployeeRecord(EmployeeDTO employeeDTO){
		personDAO.addEmployeeToDB(employeeDTO);
	}
	
	public boolean deleteEmployeeRecord(Integer employeeID){
		boolean loopRecordMenu = true;		
		
		personDAO.deleteEmployeeRecord(employeeID);
		return loopRecordMenu;
	}
	
	public boolean switchUpdateRecord(Integer employeeID, int recordInfoChoice, String newValue) throws ParseException{
		boolean loopRecordMenu = true;
			
		Employee employee = personDAO.getEmployeeRecord(employeeID);
		EmployeeDTO employeeDTO = personDAO.toDTO(employee);
		
		if(recordInfoChoice >= 1 && recordInfoChoice <= 5){
			NameDTO nameDTO = employeeDTO.getName();
			nameDTO = executeUpdateName(recordInfoChoice, newValue, nameDTO);
			employeeDTO.setName(nameDTO);				
		}
		else if(recordInfoChoice >=6 && recordInfoChoice <=9){
			AddressDTO addressDTO = employeeDTO.getAddress();
			addressDTO = executeUpdateAddress(recordInfoChoice, newValue, addressDTO);
			employeeDTO.setAddress(addressDTO);
		}
		else if(recordInfoChoice >=10 && recordInfoChoice <=13){
			OtherInfoDTO otherInfoDTO = employeeDTO.getOtherInfo();
			otherInfoDTO = executeUpdateOtherInfo(recordInfoChoice, newValue, otherInfoDTO, DATE_FLAG);
			employeeDTO.setOtherInfo(otherInfoDTO);
		}

		personDAO.updateRecord(employeeDTO, recordInfoChoice);
		return loopRecordMenu;
	}
	
	public NameDTO executeUpdateName(int recordInfoChoice, String newValue, NameDTO nameDTO){
	switch(recordInfoChoice){
		case 1:
			nameDTO.setTitle(newValue);
			break;
		case 2:
			nameDTO.setFirstName(newValue);
			break;
		case 3:
			nameDTO.setMiddleName(newValue);
			break;
		case 4:
			nameDTO.setLastName(newValue);
			break;
		case 5:
			nameDTO.setSuffix(newValue);
			break;			
	}
		
		return nameDTO;
	}
	
	public AddressDTO executeUpdateAddress(int recordInfoChoice, String newValue, AddressDTO addressDTO){
		switch(recordInfoChoice){
			case 6:
				addressDTO.setStreetNo(newValue);
				break;
			case 7:
				addressDTO.setBrgy(newValue);
				break;
			case 8:
				addressDTO.setCityMunicipality(newValue);
				break;
			case 9:
				addressDTO.setZipcode(newValue);
				break;
		}
		
		return addressDTO;
	}
	
	public OtherInfoDTO executeUpdateOtherInfo(int recordInfoChoice, String newValue, OtherInfoDTO otherInfoDTO, String DATE_FLAG) throws ParseException{		
		switch(recordInfoChoice){
			case 10:
				Date birthdayDTO = parseToDate(newValue);
				otherInfoDTO.setBirthday(birthdayDTO);
				break;
			case 11:
				double gwaDTO = Double.parseDouble(newValue);
				otherInfoDTO.setGwa(gwaDTO);
				break;
			case 12:
				Date hireDateDTO = parseToDate(newValue);
				otherInfoDTO.setHireDate(hireDateDTO);
				break;
			case 13:
				boolean isEmployedDTO = newValue.equals(DATE_FLAG) ? true:false;
				otherInfoDTO.setIsEmployed(isEmployedDTO);
				break;
		}
		
		return otherInfoDTO;
	}
	
	public boolean listEmployees(String listFlag){
		boolean loopMain = true;				
			
		System.out.print("\n\nEMPLOYEE RECORDS (ALL)\n");
		System.out.print("------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		
		if(listFlag.equals(BY_NAME)){ //by Criteria sort
			System.out.println("Sort by Last Name");
			List<EmployeeDTO> employeeListDTO = personDAO.getEmployeeListByCriteria();
			iterateEmployeeList(employeeListDTO);				
		}
		else if(listFlag.equals(BY_GWA)){ //by Comparator sort
			System.out.println("Sort by GWA");
			List<EmployeeDTO> employeeListDTO = personDAO.getEmployeeListByQuery();
			Collections.sort(employeeListDTO, EmployeeDTO.employeeGwaComparator);
			iterateEmployeeList(employeeListDTO);
		}
		else if(listFlag.equals(BY_HIRE_DATE)){ //by HQL Query sort
			System.out.println("Sort by Date Hired");
			String hql = "FROM Employee E ORDER BY E.otherInfo.hireDate";
			List<EmployeeDTO> employeeListDTO = personDAO.getEmployeeListByHqlQuery(hql);
			iterateEmployeeList(employeeListDTO);
		}			
			
		System.out.print("\n------------------------------------------------------------------------------------------------------------------------------------------------------\n\n\n");
		
		return loopMain;
	}
	
	public void iterateEmployeeList(List<EmployeeDTO> employeeListDTO){
		System.out.print("\nID  Name\t\t\t\tAddress\t\t\tOther Information\t\t\tContact Information\t   Roles");
			
		for(Iterator employeeIterator = employeeListDTO.iterator(); employeeIterator.hasNext();){
				
			EmployeeDTO employeeDTO = (EmployeeDTO)employeeIterator.next();
			NameDTO nameDTO = employeeDTO.getName();
			AddressDTO addressDTO = employeeDTO.getAddress();
			OtherInfoDTO otherInfoDTO = employeeDTO.getOtherInfo();			
		
			displayEmployeeBackground(employeeDTO, nameDTO, addressDTO, otherInfoDTO);					
		 									
		 	Set<RoleDTO> rolesDTO = employeeDTO.getRole();		 	
		 	List thisEmployeeRolesDTO = employeeRolesToList(rolesDTO);
		
			Set<ContactDTO> contactsDTO = employeeDTO.getContacts();
			Map thisEmployeeContactsDTO = employeeContactsToMap(contactsDTO);
		
			int employeeContactMapSize = thisEmployeeContactsDTO.size();
			int employeeRoleListSize = thisEmployeeRolesDTO.size();				
		
			displayContactsAndRoles(employeeContactMapSize, employeeRoleListSize, thisEmployeeContactsDTO, thisEmployeeRolesDTO, contactsDTO, rolesDTO);
			}
	}
	
	public void displayEmployeeBackground(EmployeeDTO employeeDTO, NameDTO nameDTO, AddressDTO addressDTO, OtherInfoDTO otherInfoDTO){
	
		String employeeNameField = MessageFormat.format("\n{0}   {1}. {2} {3} {4} {5}", String.valueOf(employeeDTO.getId()), (String)nameDTO.getTitle(), (String)nameDTO.getFirstName(), (String)nameDTO.getMiddleName(), (String)nameDTO.getLastName(), (String)nameDTO.getSuffix());
		String employeeAddressField = MessageFormat.format("\t{0} {1} {2} {3}",(String)addressDTO.getStreetNo(), (String)addressDTO.getBrgy(), (String)addressDTO.getCityMunicipality(), (String)addressDTO.getZipcode());
	
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date bdayDTO = otherInfoDTO.getBirthday();
		Date dateHiredDTO = otherInfoDTO.getHireDate();
		String birthdayDTO = dateFormat.format(bdayDTO);
		String hireDateDTO = dateFormat.format(dateHiredDTO);
	
		DecimalFormat twoDecimalPlace = new DecimalFormat("#.##");
		
		String employeeOtherInfoField = MessageFormat.format("\t{0} {1} {2} {3} ", birthdayDTO, hireDateDTO, String.valueOf(otherInfoDTO.getIsEmployed()), String.valueOf(twoDecimalPlace.format(otherInfoDTO.getGwa())));
	
		System.out.print(employeeNameField + employeeAddressField + employeeOtherInfoField);	
	}
	
	public void displayContactsAndRoles(int employeeContactMapSize, int employeeRoleListSize, Map thisEmployeeContactsDTO, List<RoleDTO> thisEmployeeRolesDTO, Set<ContactDTO> contactsDTO, Set<RoleDTO> rolesDTO){		
		
		if(employeeContactMapSize >= employeeRoleListSize){
			moreContactsThanRoles(contactsDTO, thisEmployeeRolesDTO);
		}				
		else if(employeeContactMapSize < employeeRoleListSize){
			moreRolesThanContacts(rolesDTO, thisEmployeeContactsDTO);
		}
			
	}
	
	public List employeeRolesToList(Set<RoleDTO> rolesDTO){	
		List thisEmployeeRolesDTO = new ArrayList();
		
		for(Iterator roleIterator = rolesDTO.iterator(); roleIterator.hasNext();){
			RoleDTO roleNameDTO = (RoleDTO)roleIterator.next();
			thisEmployeeRolesDTO.add(roleNameDTO.getRoleName());
		}	
		
		return thisEmployeeRolesDTO;
	}
	
	public Map employeeContactsToMap(Set<ContactDTO> contactsDTO){
		Map thisEmployeeContactsDTO = new HashMap();
		
		for(Iterator contactIterator = contactsDTO.iterator(); contactIterator.hasNext();){
			ContactDTO contactDetailsDTO = (ContactDTO)contactIterator.next();
			thisEmployeeContactsDTO.put(contactDetailsDTO.getContactDetails(), contactDetailsDTO.getContactType());
		}
		
		return thisEmployeeContactsDTO;
	}
	
	public void moreContactsThanRoles(Set<ContactDTO> contactsDTO, List thisEmployeeRolesDTO){
		int roleCtr = 0,
				roleCtrChecker = 0;
		for(Iterator contactIterator = contactsDTO.iterator(); contactIterator.hasNext();){
			String thisEmployeeRoleNameDTO = "";
			ContactDTO contactDetailsDTO = (ContactDTO)contactIterator.next();
		
			if(roleCtr == roleCtrChecker){
				try{
					thisEmployeeRoleNameDTO = (String)thisEmployeeRolesDTO.get(roleCtr);
				}catch(IndexOutOfBoundsException iobe){
					System.out.print("");
				}		
			}					
			System.out.println("\t[" + contactDetailsDTO.getContactType() + "]    " + contactDetailsDTO.getContactDetails() + "\t   " + thisEmployeeRoleNameDTO);
			System.out.print("\t\t\t\t\t\t\t\t\t\t\t         ");
			roleCtrChecker++;
			roleCtr++;
			try{
				thisEmployeeRolesDTO.get(roleCtr);
			}catch(IndexOutOfBoundsException iobe){
				System.out.print("");
				roleCtr--;
			}
		}
	}
	
	public void moreRolesThanContacts(Set<RoleDTO> rolesDTO, Map thisEmployeeContactsDTO){
		String openBrace = "\t[",
						closeBrace = "]    ",
						tabSequence1 = "\t",
						tabSequence2 = "\t\t\t\t\t\t\t\t\t\t\t         ";
	
		Set set = thisEmployeeContactsDTO.entrySet();
		Iterator mapIterator = set.iterator();
		
		for(Iterator roleIterator = rolesDTO.iterator(); roleIterator.hasNext();){
			String thisEmployeeContactDetailsDTO = "";
			String thisEmployeeContactTypeDTO = "";
			RoleDTO roleDetailsDTO = (RoleDTO)roleIterator.next();			
		
			try{
				Map.Entry mapEntry = (Map.Entry)mapIterator.next();
				thisEmployeeContactDetailsDTO = (String)mapEntry.getKey();
				thisEmployeeContactTypeDTO = (String)mapEntry.getValue();
			}catch(NoSuchElementException nsee){
				System.out.print("");
				openBrace = "";
				closeBrace = "";
				tabSequence1 = "\t\t\t\t   ";
				tabSequence2 = "\t\t\t\t\t\t\t\t\t\t\t   ";
			}
		
			System.out.println(openBrace + thisEmployeeContactTypeDTO + closeBrace + thisEmployeeContactDetailsDTO + tabSequence1 + roleDetailsDTO.getRoleName());
			System.out.print(tabSequence2);
		}		
	}
	
	public static Date parseToDate(String day) throws ParseException{
		final String DATE_PATTERN = "yyyy/MM/dd";
		SimpleDateFormat dayFormatter = new SimpleDateFormat(DATE_PATTERN);
		Date date =  dayFormatter.parse(day);
		return date;
	}
	
	public void displayRecordInfo(Integer employeeID){
		
		Employee employee = personDAO.getEmployeeRecord(employeeID);
		EmployeeDTO employeeDTO = personDAO.toDTO(employee);
		NameDTO nameDTO = employeeDTO.getName();
		AddressDTO addressDTO = employeeDTO.getAddress();
		OtherInfoDTO otherInfoDTO = employeeDTO.getOtherInfo();
	
		displayNameInfo(nameDTO);
		displayAddressInfo(addressDTO);
		displayOtherInfo(otherInfoDTO);		
	}
	
	public void displayNameInfo(NameDTO nameDTO){
		System.out.println("\n\t\tPersonal Information");
		System.out.println("\t\t[1]  Title       : " + nameDTO.getTitle());
		System.out.println("\t\t[2]  First Name  : " + nameDTO.getFirstName());
		System.out.println("\t\t[3]  Middle Name : " + nameDTO.getMiddleName());
		System.out.println("\t\t[4]  Last Name   : " + nameDTO.getLastName());
		System.out.println("\t\t[5]  Suffix      : " + nameDTO.getSuffix());
	}
	
	public void displayAddressInfo(AddressDTO addressDTO){
		System.out.println("\t\tAddress Information");
		System.out.println("\t\t[6]  Street No.        : " + addressDTO.getStreetNo());
		System.out.println("\t\t[7]  Brgy              : " + addressDTO.getBrgy());
		System.out.println("\t\t[8]  City/Municipality : " + addressDTO.getCityMunicipality());
		System.out.println("\t\t[9]  Zipcode           : " + addressDTO.getZipcode());
	}
	
	public void displayOtherInfo(OtherInfoDTO otherInfoDTO){
		System.out.println("\t\tOther Information");
		System.out.println("\t\t[10] Birthday           : " + otherInfoDTO.getBirthday());
			
		DecimalFormat twoDecimalPlace = new DecimalFormat("#.##");
		System.out.println("\t\t[11] GWA                : " + twoDecimalPlace.format(otherInfoDTO.getGwa()));
		System.out.println("\t\t[12] Date Hired         : " + otherInfoDTO.getHireDate());
		System.out.println("\t\t[13] Currently Employed : " + otherInfoDTO.getIsEmployed());
	}	
	
	public void showRecordShortList(){
		List<EmployeeDTO> employeeShortListDTO = personDAO.showRecordShortList();
		
		System.out.println("\n\tEmployee ID\tEmployee Name");
			for(Iterator employeeIterator = employeeShortListDTO.iterator(); employeeIterator.hasNext();){
				EmployeeDTO employeeDTO = (EmployeeDTO)employeeIterator.next();
				NameDTO nameDTO = employeeDTO.getName();
				
				System.out.println("\t     " + employeeDTO.getId() + "\t\t" + nameDTO.getTitle() + " " +nameDTO.getFirstName() + " " + nameDTO.getMiddleName() + " " + nameDTO.getLastName() + " " + nameDTO.getSuffix());
				
			}
	}
	
	public void searchByLastName(String searchCriteria){
		List<EmployeeDTO> employeeListDTO = personDAO.searchByLastName(searchCriteria);
		iterateEmployeeList(employeeListDTO);
	}
	
	public void searchByGwa(double lowCriteria, double hiCriteria){
		List<EmployeeDTO> employeeListDTO = personDAO.searchByGwa(lowCriteria, hiCriteria);
		iterateEmployeeList(employeeListDTO);
	}
	
	public void searchByHireDate(String tempSearchCriteria){
		List<EmployeeDTO> employeeListDTO = personDAO.searchByHireDate(tempSearchCriteria);
		iterateEmployeeList(employeeListDTO);
	}
}
