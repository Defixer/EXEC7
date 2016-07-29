package com.hibernate.exercise6.app;

import com.hibernate.exercise6.model.Employee;
import com.hibernate.exercise6.model.Name;
import com.hibernate.exercise6.model.Address;
import com.hibernate.exercise6.model.OtherInfo;
import com.hibernate.exercise6.model.Contact;
import com.hibernate.exercise6.model.Role;
import com.hibernate.exercise6.service.EmployeeService;
import com.hibernate.exercise6.service.RoleService;
import com.hibernate.exercise6.service.ContactService;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import org.hibernate.HibernateException;
import org.hibernate.StaleStateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GUIMain{
	private static Scanner scan = new Scanner(System.in);
	
	private static final String CONTACT_TYPE_LANDLINE = "landline",
															CONTACT_TYPE_MOBILE = "mobile",
															CONTACT_TYPE_EMAIL = "email",
															DATE_FLAG = "y",
															BY_NAME = "by name",
															BY_GWA = "by gwa",
															BY_HIRE_DATE = "by hire date";
															
	private static EmployeeService employeeService = new EmployeeService();
	private static RoleService roleService = new RoleService();
	private static ContactService contactService = new ContactService();
	private static HibernateUtil hibernateUtil = new HibernateUtil();
	
	private static GUIMain gui = new GUIMain();
	
	public static void main(String[] args) throws Exception{		
		boolean loopMainMenu = true;
		SessionFactory factory = hibernateUtil.getSessionFactory();
		
		Thread.sleep(1500);
		
		while(loopMainMenu){			
		
			int mainMenuChoice = gui.displayMainMenu();
			
			switch(mainMenuChoice){
				case 1:
					loopMainMenu = gui.manageRecords();
					break;
				case 2:	
					loopMainMenu = gui.manageContacts();
					break;
				case 3:
					loopMainMenu = gui.manageRoles();
					break;
				case 4:
					loopMainMenu = false;
					break;
					
			}					
		}
		
		factory.close();	
	}
	
	public int displayMainMenu(){
		int mainMenuChoice = 0;
		System.out.println("\nEmployee Information System");
		System.out.println("[1] Manage Records");
		System.out.println("[2] Manage Contacts");
		System.out.println("[3] Manage Role");
		System.out.println("[4] Exit");
		System.out.print("Enter choice: ");
		mainMenuChoice = scan.nextInt(); scan.nextLine();
		
		return mainMenuChoice;
	}
	
	public int displayRecordsMenu(){
		int recordMenuChoice = 0;
		
		System.out.println("\n\tManage Records");
		System.out.println("\t[1] Add new Record");
		System.out.println("\t[2] Update existing Record");
		System.out.println("\t[3] Delete Record");
		System.out.println("\t[4] List All Record");
		System.out.println("\t[5] Search Employee Record");
		System.out.println("\t[6] Back");
		System.out.print("\tEnter choice: ");
		recordMenuChoice = scan.nextInt(); scan.nextLine();
		
		return recordMenuChoice;
	}
	
	public boolean manageRecords() throws ParseException{
		boolean loopRecordMenu = true;
		boolean loopMainMenu = true;
		Integer employeeID;
		int recordInfoChoice = 0;
		String newValue = "";
		
		while(loopRecordMenu){
			
			int recordMenuChoice = displayRecordsMenu();
		
			switch(recordMenuChoice){
				case 1:
					loopRecordMenu = newEmployeeRecord();
					break;
				case 2:
					employeeService.showRecordShortList();
					employeeID = inputEmployeeID();
					employeeService.displayRecordInfo(employeeID);
					System.out.print("\t\tEnter choice to be updated: ");
					recordInfoChoice = scan.nextInt(); scan.nextLine();
					newValue = inputNewValue();
					loopRecordMenu = employeeService.switchUpdateRecord(employeeID, recordInfoChoice, newValue);
					employeeService.displayRecordInfo(employeeID);
					break;
				case 3:
					employeeService.showRecordShortList();
					employeeID = inputEmployeeID();
					loopRecordMenu = employeeService.deleteEmployeeRecord(employeeID);
					break;
				case 4:
					loopRecordMenu = displayListMenu();
					break;
				case 5:
					loopRecordMenu = searchEmployees();
					break;
				case 6:
					loopRecordMenu = false;
					break;
			}
		}
		return loopMainMenu;
	}
	
	/*<ADD NEW RECORD>*/
	public boolean newEmployeeRecord() throws ParseException{
		boolean loopMain = true;
		Set<ContactDTO> tempContactsDTO = new HashSet();
	
		NameDTO nameInfoDTO = inputPersonalInfo();
		AddressDTO addressInfoDTO = inputAddressInfo();
		OtherInfoDTO otherInfoDTO = inputOtherInfo();		
		Set<ContactDTO> contactsDTO = contactInfoMenu(tempContactsDTO);
		EmployeeDTO employeeDTO = new EmployeeDTO(nameInfoDTO, addressInfoDTO, otherInfoDTO);
		employeeDTO.setContacts(contactsDTO);
		
		employeeService.createEmployeeRecord(employeeDTO);
		return loopMain;
	}
	
	public NameDTO inputPersonalInfo(){
		String title,
						firstName,
						lastName,
						middleName,
						suffix;
		
		System.out.println("\t\tPersonal Information");
		System.out.print("\t\t\tEnter title\t\t     : ");
		title = scan.nextLine();		
		System.out.print("\t\t\tEnter first name\t     : ");
		firstName = scan.nextLine();
		System.out.print("\t\t\tEnter middle name\t     : ");
		middleName = scan.nextLine();
		System.out.print("\t\t\tEnter last name\t\t     : ");
		lastName = scan.nextLine();
		System.out.print("\t\t\tEnter suffix (if applicable) : ");
		suffix = scan.nextLine();		
		
		NameDTO nameInfoDTO = new NameDTO(title, firstName, middleName, lastName, suffix);
		
		return nameInfoDTO;
	}
	
	public AddressDTO inputAddressInfo(){
		String streetNo,
						brgy,
						cityMunicipality,
						zipcode;
		
		System.out.println("\t\tAddress");
		System.out.print("\t\t\tEnter Street No\t\t  : ");
		streetNo = scan.nextLine();
		System.out.print("\t\t\tEnter Barangay\t\t  : ");
		brgy = scan.nextLine();
		System.out.print("\t\t\tEnter City / Municipality : ");
		cityMunicipality = scan.nextLine();
		System.out.print("\t\t\tEnter ZIP Code\t\t  : ");
		zipcode = scan.nextLine();
		
		AddressDTO addressInfoDTO = new AddressDTO(streetNo, brgy, cityMunicipality, zipcode);
		
		return addressInfoDTO;
	}
	
	public OtherInfoDTO inputOtherInfo() throws ParseException{
		String bday,						
						dateHired,
						isWorking;
		double gwa;
		Date birthday,
					hireDate;
		boolean isEmployed;
		
		System.out.println("\t\tOther Information");
		System.out.print("\t\t\tEnter Birthday (YYYY/MM/DD)    : ");
		bday = scan.nextLine();
		birthday = employeeService.parseToDate(bday);
		System.out.print("\t\t\tEnter General Weigthed Average : ");
		gwa = scan.nextDouble(); scan.nextLine();
		System.out.print("\t\t\tEnter Date Hired (YYYY/MM/DD)  : ");
		dateHired = scan.nextLine();
		hireDate = employeeService.parseToDate(dateHired);
		System.out.print("\t\t\tCurrent Employed? (Y/N)\t       : ");
		isWorking = scan.nextLine();
		isEmployed = employmentStatus(isWorking);
		
		OtherInfoDTO otherInfoDTO = new OtherInfoDTO(birthday, hireDate, gwa, isEmployed);
		
		return otherInfoDTO;
	}
	
	public static boolean employmentStatus(String isWorking){		
		if(isWorking.equalsIgnoreCase(DATE_FLAG)){
			return true;
		}
		return false;
	}
	
	public Set<ContactDTO> contactInfoMenu(Set<ContactDTO> contactsDTO){		
		
		String contactInfoFlag = "";	
		boolean inputNotDone = true;		
		
		while(inputNotDone){
			int contactsMenuChoice = 0;
			
			System.out.println("\t\tContact Details");
			System.out.println("\t\t[1] Add new Landline");
			System.out.println("\t\t[2] Add new Mobile");
			System.out.println("\t\t[3] Add new Email");
			System.out.println("\t\t[4] Back");
			System.out.print("\t\tEnter choice: ");			
			contactsMenuChoice = scan.nextInt(); scan.nextLine();
			
			switch(contactsMenuChoice){
				case 1:
					contactInfoFlag = "landline";
					contactsDTO = addContactInfo(contactInfoFlag,contactsDTO);
					break;
				case 2:
					contactInfoFlag = "mobile";
					contactsDTO = addContactInfo(contactInfoFlag,contactsDTO);
					break;
				case 3:
					contactInfoFlag = "email";
					contactsDTO = addContactInfo(contactInfoFlag,contactsDTO);
					break;
				case 4:
					inputNotDone = false;
					break;
			}		
		}
		
		return contactsDTO;		
	}
	
	public Set<ContactDTO> addContactInfo(String contactInfoFlag, Set<ContactDTO> contactsDTO){
		String contactInfo = "";	
		
		contactInfo = inputContactInfo(contactInfoFlag);
		contactsDTO.add(new ContactDTO(contactInfo, contactInfoFlag));		
		
		return contactsDTO;
	}
	
	public String inputContactInfo(String contactInfoFlag){
		String contactInfo,
						wordNumber = "",
						wordAddress = "";
		
		if(contactInfoFlag.equals(CONTACT_TYPE_MOBILE)){
			wordNumber = " Number";
		}
		else if(contactInfoFlag.equals(CONTACT_TYPE_EMAIL)){
			wordAddress = " Address";
		}
		
		System.out.print("\n\t\t\tEnter new " + contactInfoFlag + wordNumber + wordAddress + ": ");
		contactInfo = scan.nextLine();
		
		return contactInfo;		
	}	
	
	/*</ADD NEW RECORD>*/
	
	/*<DISPLAY RECORDS>*/
	public boolean displayListMenu(){
		int listMenuChoice = 0;
		boolean loopRecordMenu = true,
						loopListMenu = true;
		
		System.out.println("\n\t\tList Record");
		System.out.println("\t\t[1] Sort by Last Name");
		System.out.println("\t\t[2] Sort by GWA");
		System.out.println("\t\t[3] Sort Hire Date");
		System.out.print("\tEnter choice: ");
		listMenuChoice = scan.nextInt();
		
		switch(listMenuChoice){
			case 1:
				loopListMenu = employeeService.listEmployees(BY_NAME);
				break;
			case 2:
				loopListMenu = employeeService.listEmployees(BY_GWA);
				break;
			case 3:
				loopListMenu = employeeService.listEmployees(BY_HIRE_DATE);
				break;
			case 4:
				loopListMenu = false;
				break;			
		}
		
		return loopRecordMenu;		
	}
	/*</DISPLAY RECORDS>*/
	
	/*<SEARCH RECORDS>*/
	public boolean searchEmployees(){
		boolean loopRecordMenu = true;
		int searchRecordChoice = 0;
		String searchCriteria = "",
						datePattern = "";
		double lowCriteria = 0,
						hiCriteria = 0;
		
		System.out.println("\n\tSEARCH EMPLOYEE RECORD");
		System.out.println("\t[1] By Last Name");
		System.out.println("\t[2] By GWA");
		System.out.println("\t[3] By Hire Date");
		System.out.print("\tEnter choice: ");
		searchRecordChoice = scan.nextInt(); scan.nextLine();
		if(searchRecordChoice == 3){
			datePattern = "(YYYY-MM-DD)";
		}
		System.out.print("\tEnter search criteria " + datePattern + ": ");
		String tempSearchCriteria = scan.nextLine();
		
		if(searchRecordChoice == 1){
			searchCriteria = "%" + tempSearchCriteria + "%";
		}		
		else if(searchRecordChoice == 2){
			lowCriteria = Double.parseDouble(tempSearchCriteria) - 0.01;
			hiCriteria = Double.parseDouble(tempSearchCriteria) + 0.01;	
		}
		
		switch(searchRecordChoice){
			case 1:
				employeeService.searchByLastName(searchCriteria);
				break;
			case 2:
				employeeService.searchByGwa(lowCriteria, hiCriteria);
				break;
			case 3:
				employeeService.searchByHireDate(tempSearchCriteria);
				break;
		}
		
		
		return loopRecordMenu;
	}
	/*</SEARCH RECORDS>*/
	
	
	public String inputNewValue(){
		String newValue = "";
		
		System.out.print("\t\tEnter new value: ");
		newValue = scan.nextLine();
		
		return newValue;
	}
	
	public int displayContactMenu(){
		int contactMenuChoice = 0;
		
		System.out.println("\n\tManage Contact");
		System.out.println("\t[1] Add Contact to Employee");
		System.out.println("\t[2] Update Contact of Employee");
		System.out.println("\t[3] Delete Contact from Employee");
		System.out.println("\t[4] Back");
		System.out.print("\tEnter choice: ");
		contactMenuChoice = scan.nextInt(); scan.nextLine();
		
		return contactMenuChoice;
	}
	
	public boolean manageContacts(){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		boolean loopContactMenu = true,
						loopMainMenu = true;	
		Integer employeeID;
		int contactID = 0;
		String newValue = "",
						contactType = "",
						contactDetails = "";
		
		while(loopContactMenu){
			int contactMenuChoice = displayContactMenu();
			
			switch(contactMenuChoice){
				case 1:
					employeeService.showRecordShortList();
					employeeID = inputEmployeeID();
					contactService.displayEmployeeContact(employeeID);
					int contactInfoChoice = inputContactInfoChoice();
					switch(contactInfoChoice){
						case 1:
							contactType = CONTACT_TYPE_LANDLINE;
							System.out.print("\t\tEnter value: ");
							contactDetails = scan.nextLine();
							loopContactMenu = contactService.addContactsToEmployee(employeeID, contactType, contactDetails);
							break;
						case 2:
							contactType = CONTACT_TYPE_MOBILE;
							System.out.print("\t\tEnter value: ");
							contactDetails = scan.nextLine();
							loopContactMenu = contactService.addContactsToEmployee(employeeID, contactType, contactDetails);
							break;
						case 3:
							contactType = CONTACT_TYPE_EMAIL;
							System.out.print("\t\tEnter value: ");
							contactDetails = scan.nextLine();
							loopContactMenu = contactService.addContactsToEmployee(employeeID, contactType, contactDetails);
							break;
						case 4:
							loopContactMenu = false;
							break;			
					}
					contactService.displayEmployeeContact(employeeID);
					break;
				case 2:
					employeeService.showRecordShortList();
					employeeID = inputEmployeeID();
					contactService.displayEmployeeContact(employeeID);
					System.out.print("\t\tEnter choice to be updated: ");
					contactID = scan.nextInt(); scan.nextLine();
					System.out.print("\t\tEnter new value: ");
					newValue = scan.nextLine();
					loopContactMenu = contactService.switchUpdateContact(employeeID, contactID, newValue);
					contactService.displayEmployeeContact(employeeID);
					break;
				case 3:
					employeeService.showRecordShortList();
					employeeID = inputEmployeeID();
					contactService.displayEmployeeContact(employeeID);
					System.out.print("\t\tEnter choice to be deleted: ");
					contactID = scan.nextInt(); scan.nextLine();
					loopContactMenu = contactService.deleteContactFromEmployee(employeeID, contactID);
					contactService.displayEmployeeContact(employeeID);									
					break;
				case 4:
					loopContactMenu = false;
					break;
			}
		}
			
		return loopMainMenu;
	}
	
	public int inputContactInfoChoice(){
		int contactInfoChoice = 0;
		
		System.out.println("\n\t\tContact Details");
		System.out.println("\t\t[1] Add new Landline");
		System.out.println("\t\t[2] Add new Mobile");
		System.out.println("\t\t[3] Add new Email");
		System.out.println("\t\t[4] Back");
		System.out.print("\t\tEnter choice: ");
		contactInfoChoice = scan.nextInt(); scan.nextLine();
		
		return contactInfoChoice;
	}
	
	
	
	public int displayRoleMenu(){
		int roleMenuChoice = 0;
		
		System.out.println("\n\tManage Role");
		System.out.println("\t[1] Add Role to Employee");
		System.out.println("\t[2] Delete Role from Employee");
		System.out.println("\t[3] Add New Role");
		System.out.println("\t[4] Delete Existing Role");
		System.out.println("\t[5] Update Existing Role");
		System.out.println("\t[6] List ALL Roles-Employees");
		System.out.println("\t[7] Back");
		System.out.print("\tEnter choice: ");
		roleMenuChoice = scan.nextInt(); scan.nextLine();
		
		return roleMenuChoice;
	}
	
	public boolean manageRoles(){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		boolean loopRoleMenu = true,
						loopMainMenu = true;
		
		while(loopRoleMenu){
			int roleMenuChoice = displayRoleMenu();
			int roleID = 0;
			Integer employeeID;
			String roleName;
			
			switch(roleMenuChoice){
				case 1:
					employeeService.showRecordShortList();
					employeeID = inputEmployeeID();		
					roleService.displayEmployeeRole(employeeID);			
					roleService.roleList();
					System.out.print("\t\tEnter choice to be added: ");
					roleID = scan.nextInt(); scan.nextLine();
					loopRoleMenu = roleService.addRoleToEmployee(employeeID, roleID);
					roleService.displayEmployeeRole(employeeID);
					break;
				case 2:
					employeeService.showRecordShortList();
					employeeID = inputEmployeeID();
					roleService.displayEmployeeRole(employeeID);		
					System.out.print("\t\tEnter choice to be deleted: ");
					roleID = scan.nextInt(); scan.nextLine();					
					loopRoleMenu = roleService.deleteRoleFromEmployee(employeeID, roleID);			
					roleService.displayEmployeeRole(employeeID);
					break;
				case 3:
					roleService.roleList();
					System.out.print("\t\tEnter new role name: ");
					roleName = scan.nextLine();
					loopRoleMenu = roleService.addNewRoleToDB(roleName);
					roleService.roleList();
					break;
				case 4:
					roleService.roleList();
					System.out.print("\t\tEnter choice to be deleted: ");
					roleID = scan.nextInt(); scan.nextLine();
					loopRoleMenu = roleService.deleteRoleFromDB(roleID);
					roleService.roleList();
					break;
				case 5:
					roleService.roleList();
					System.out.print("\t\tEnter choice to be updated: ");
					roleID = scan.nextInt(); scan.nextLine();
					System.out.print("\t\tEnter new value: ");
					roleName = scan.nextLine();
					loopRoleMenu = roleService.switchUpdateRoles(roleID, roleName);
					roleService.roleList();
					break;
				case 6:
					loopRoleMenu = roleService.displayRoleEmployeesList();
					break;
				case 7:
					loopRoleMenu = false;
					break;
				
			}
		}
		
		return loopMainMenu;		
	}
	
	public Integer inputEmployeeID(){
		int employeeID = 0;
		
		System.out.print("\tEnter employee ID: ");
		employeeID = scan.nextInt(); scan.nextLine();
		
		return (Integer)employeeID;
	}
}
