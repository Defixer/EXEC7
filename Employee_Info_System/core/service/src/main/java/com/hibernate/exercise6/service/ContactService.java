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
import com.hibernate.exercise6.dto.ContactDTO;
import com.hibernate.exercise6.dto.RoleDTO;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.MessageFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.NoSuchElementException;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ContactService{
	PersonDAO personDAO = new PersonDAO();
	private HibernateUtil hibernateUtil = new HibernateUtil();
	
	public boolean addContactsToEmployee(Integer employeeID, String contactType, String contactDetails){
		boolean loopContactMenu = true;
		
		Employee employee = personDAO.getEmployeeRecord(employeeID);
		EmployeeDTO employeeDTO = personDAO.toDTO(employee);
		Set<ContactDTO> contactsDTO = employeeDTO.getContacts();
		ContactDTO newContactDTO = new ContactDTO(contactDetails, contactType);
		contactsDTO.add(newContactDTO);
		personDAO.addContacts(employeeDTO);
		return loopContactMenu;
	}
	
	public boolean deleteContactFromEmployee(Integer employeeID, int contactID){
		boolean loopContactMenu = true;
		
		Employee employee = personDAO.getEmployeeRecord(employeeID);
		Contact thisContact = personDAO.getEmployeeContact(contactID);
		Set<Contact> contacts = employee.getContacts();
		//contacts.remove(thisContact);		
		personDAO.deleteContactInDB(thisContact);
		personDAO.deleteContact(employee);
		return loopContactMenu;
	}	
	
	public boolean switchUpdateContact(Integer employeeID, int contactID, String newValue){
		boolean loopContactMenu = true;
		
		Employee employee = personDAO.getEmployeeRecord(employeeID);
		EmployeeDTO employeeDTO = personDAO.toDTO(employee);
		Set<ContactDTO> contactsDTO = employeeDTO.getContacts();		
		
		Contact thisContact = personDAO.getEmployeeContact(contactID);
		
		for(Iterator contactDTOIterator = contactsDTO.iterator(); contactDTOIterator.hasNext();){
			ContactDTO thisContactDTO = (ContactDTO)contactDTOIterator.next();
			if(thisContact.getId() == thisContactDTO.getId()){
				thisContactDTO.setContactDetails(newValue);
				personDAO.updateContactInDB(thisContactDTO);
			}
		}
		
		employeeDTO.setContacts(contactsDTO);		
		personDAO.updateContact(employeeDTO);	
		return loopContactMenu;
	}
	
	public void displayEmployeeContact(Integer employeeID){
		
		Employee employee = personDAO.getEmployeeRecord(employeeID);
		EmployeeDTO employeeDTO = personDAO.toDTO(employee);
		Set<ContactDTO> contactsDTO = employeeDTO.getContacts();
		System.out.println("\n\t\tContacts");
		for(Iterator contactIterator = contactsDTO.iterator(); contactIterator.hasNext();){
			ContactDTO contactDetailsDTO = (ContactDTO)contactIterator.next();
			System.out.println("\t\t[" + contactDetailsDTO.getId() + "] Type: " + contactDetailsDTO.getContactType() + "\t" + contactDetailsDTO.getContactDetails());
		}
	}
}
