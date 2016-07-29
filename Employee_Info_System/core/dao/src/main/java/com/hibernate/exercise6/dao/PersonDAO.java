package com.hibernate.exercise6.dao;

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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PersonDAO{
	private Scanner scan = new Scanner(System.in);
	private final String DATE_FLAG = "y";
	private HibernateUtil hibernateUtil = new HibernateUtil();
	private Employee employee;
	private Criteria criteria;
	private Query query;
	private List<Employee> employeeList;
	private Contact thisContact;
	
	public Employee getEmployeeRecord(Integer employeeID){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();
			employee = (Employee)session.get(Employee.class, employeeID);
			
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		return employee;		
	}
	
	public Contact getEmployeeContact(int contactID){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();
			thisContact = (Contact)session.get(Contact.class, contactID);
			
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		return thisContact;		
	}
	
	public void updateEmployeeRecordToDB(Employee employee){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();
			session.update(employee);
			transaction.commit();
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	public List<EmployeeDTO> getEmployeeListByCriteria(){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();
			criteria = session.createCriteria(Employee.class);
			criteria.addOrder(Order.asc("name.lastName"));
			employeeList = criteria.list();			
			transaction.commit();
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		List<EmployeeDTO> employeeListDTO = new ArrayList<>();
		for(Employee emp: employeeList){
			employeeListDTO.add(toDTO(emp));
		}
		
		return employeeListDTO;
	}
	
	public List<EmployeeDTO> getEmployeeListByHqlQuery(String hql){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();
			query = session.createQuery(hql).setCacheable(true);
			employeeList = query.list();
			transaction.commit();
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		List<EmployeeDTO> employeeListDTO = new ArrayList<>();
		for(Employee emp: employeeList){
			employeeListDTO.add(toDTO(emp));
		}
		
		return employeeListDTO;
	}
	
	public List<EmployeeDTO> getEmployeeListByQuery(){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();
			employeeList = session.createQuery("FROM Employee").setCacheable(true).list();
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		List<EmployeeDTO> employeeListDTO = new ArrayList<>();
		for(Employee emp: employeeList){
			employeeListDTO.add(toDTO(emp));
		}
		
		return employeeListDTO;
	}
	
	public void addEmployeeToDB(EmployeeDTO employeeDTO){	
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		try{
			transaction = session.beginTransaction();
			Employee employee = toEntity(employeeDTO);
			session.save(employee);
			transaction.commit();
			System.out.println("\t\tRecord successfully created");
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	public void deleteEmployeeRecord(Integer employeeID){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();	
			Employee employee = (Employee)session.get(Employee.class, employeeID);		
			session.delete(employee);		
			transaction.commit();
			System.out.println("\t\tRecord successfully deleted");			
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	public void updateRecord(EmployeeDTO employeeDTO, int recordInfoChoice) throws ParseException{
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();						
			
			if(recordInfoChoice >= 1 && recordInfoChoice <= 5){
				Employee employee = toEntity(employeeDTO);			
				session.update(employee);	
				transaction.commit();
				System.out.println("\t\tRecord successfully updated");
			}
			else if(recordInfoChoice >= 6 && recordInfoChoice <= 9){		
				Employee employee = toEntity(employeeDTO);		
				session.update(employee);	
				transaction.commit();
				System.out.println("\t\tRecord successfully updated");
			}
			else if(recordInfoChoice >= 10 && recordInfoChoice <= 13){	
				Employee employee = toEntity(employeeDTO);		
				session.update(employee);	
				transaction.commit();
				System.out.println("\t\tRecord successfully updated");
			}
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}	
	}
	
	public static Date parseToDate(String day) throws ParseException{
		final String DATE_PATTERN = "yyyy/MM/dd";
		SimpleDateFormat dayFormatter = new SimpleDateFormat(DATE_PATTERN);
		Date date =  dayFormatter.parse(day);
		return date;
	}
	
	/*CONTACTS*/
	public void addContacts(EmployeeDTO employeeDTO){		
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();		
			Employee employee = toEntity(employeeDTO);	
			session.update(employee);
			transaction.commit();
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	public void updateContact(EmployeeDTO employeeDTO){		
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();		
			Employee employee = toEntity(employeeDTO);	
			session.update(employee);
			transaction.commit();
			System.out.println("\t\tContact successfully updated");		
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}		
	}
	
	public void updateContactInDB(ContactDTO thisContactDTO){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();
			Contact thisContact = contactToEntity(thisContactDTO);
			session.update(thisContact);
			transaction.commit();
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	public Contact contactToEntity(ContactDTO contactDTO){
		Contact thisContact = new Contact();
		
		thisContact.setId(contactDTO.getId());
		thisContact.setContactDetails(contactDTO.getContactDetails());
		thisContact.setContactType(contactDTO.getContactType());
		
		return thisContact;
	}
	
	public ContactDTO contactToDTO(Contact thisContact){
		ContactDTO thisContactDTO = new ContactDTO();
		
		thisContactDTO.setId(thisContact.getId());
		thisContactDTO.setContactDetails(thisContact.getContactDetails());
		thisContactDTO.setContactType(thisContact.getContactType());
		
		return thisContactDTO;
	}
	
	public void deleteContactInDB(Contact thisContact){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();
			session.delete(thisContact);
			transaction.commit();
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	public void deleteContact(Employee employee){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();			
			session.update(employee);
			transaction.commit();
			System.out.println("\tContact successfully deleted");			
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}	
	}
	
	public String inputNewValue(){
		String newValue = "";
		
		System.out.print("\tEnter new value: ");
		newValue = scan.nextLine();
		
		return newValue;
	}
	
	public List<EmployeeDTO> showRecordShortList(){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session  = factory.openSession();
		Transaction transaction = null;
		
		List<EmployeeDTO> employeeShortListDTO = new ArrayList();
		
		try{
			transaction = session.beginTransaction();
			List<Employee> employeeShortList = session.createQuery("FROM Employee").list();
			
			for(Employee emp: employeeShortList){
				employeeShortListDTO.add(toDTO(emp));
			}
			
			transaction.commit();
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return employeeShortListDTO;				
	}
	
	public List<EmployeeDTO> searchByLastName(String searchCriteria){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		
		List<Employee> employeeList = session.createCriteria(Employee.class)
		.add(Restrictions.like("name.lastName", searchCriteria))
		.addOrder(Order.asc("name.lastName"))
		.list();
		
		List<EmployeeDTO> employeeListDTO = new ArrayList<>();
		for(Employee emp: employeeList){
			employeeListDTO.add(toDTO(emp));
		}
		
		return employeeListDTO;
	}
	
	public List<EmployeeDTO> searchByGwa(double lowCriteria, double hiCriteria){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		
		List<Employee> employeeList = session.createCriteria(Employee.class)
		.add(Restrictions.between("otherInfo.gwa", lowCriteria, hiCriteria))
		.addOrder(Order.asc("otherInfo.gwa"))
		.list();
		
		List<EmployeeDTO> employeeListDTO = new ArrayList<>();
		for(Employee emp: employeeList){
			employeeListDTO.add(toDTO(emp));
		}
		
		return employeeListDTO;
	}
	
	public List<EmployeeDTO> searchByHireDate(String tempSearchCriteria){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		
		String hql = "FROM Employee E WHERE E.otherInfo.hireDate = '" + tempSearchCriteria + "'";
		Query query = session.createQuery(hql);
		List<Employee> employeeList = query.list();
		
		List<EmployeeDTO> employeeListDTO = new ArrayList<>();
		for(Employee emp: employeeList){
			employeeListDTO.add(toDTO(emp));
		}
		
		return employeeListDTO;
	}
	
	public Employee toEntity(EmployeeDTO employeeDTO){
		Employee employee = new Employee();
		Name nameInfo = new Name();
		Address addressInfo = new Address();
		OtherInfo otherInfo = new OtherInfo();
		Set<Contact> contacts = new HashSet();
		Set<Role> roles = new HashSet();
		
		NameDTO nameDTO = employeeDTO.getName();
		nameInfo.setTitle(nameDTO.getTitle());
		nameInfo.setFirstName(nameDTO.getFirstName());
		nameInfo.setMiddleName(nameDTO.getMiddleName());
		nameInfo.setLastName(nameDTO.getLastName());
		nameInfo.setSuffix(nameDTO.getSuffix());

		AddressDTO addressDTO = employeeDTO.getAddress();
		addressInfo.setStreetNo(addressDTO.getStreetNo());
		addressInfo.setBrgy(addressDTO.getBrgy());
		addressInfo.setCityMunicipality(addressDTO.getCityMunicipality());
		addressInfo.setZipcode(addressDTO.getZipcode());
		
		OtherInfoDTO otherInfoDTO = employeeDTO.getOtherInfo();
		otherInfo.setBirthday(otherInfoDTO.getBirthday());
		otherInfo.setHireDate(otherInfoDTO.getHireDate());
		otherInfo.setGwa(otherInfoDTO.getGwa());
		otherInfo.setIsEmployed(otherInfoDTO.getIsEmployed());		
		
		if(employeeDTO.getContacts() != null){
			for(ContactDTO contactDTO: employeeDTO.getContacts()){
				Contact thisContact = new Contact();				
				thisContact.setId(contactDTO.getId());
				thisContact.setContactDetails(contactDTO.getContactDetails());
				thisContact.setContactType(contactDTO.getContactType());
				contacts.add(thisContact);				
			}
			
			/*Set<ContactDTO> contactsDTO = employeeDTO.getContacts();
			
			for(Iterator contactDTOIterator = contactsDTO.iterator(); contactDTOIterator.hasNext();){
				Contact thisContact = new Contact();
				ContactDTO thisContactDTO = (ContactDTO)contactDTOIterator.next();
				
				String contactDetails = thisContactDTO.getContactDetails();
				String contactType = thisContactDTO.getContactType();
				//int id = thisContactDTO.getId();
				
				thisContact.setContactDetails(contactDetails);
				thisContact.setContactType(contactType);
				//thisContact.setId(id);
				contacts.add(thisContact);
			}*/			
		}
		
		if(employeeDTO.getRole() != null){
			for(RoleDTO roleDTO: employeeDTO.getRole()){
				Role thisRole = new Role();
				thisRole.setId(roleDTO.getId());
				thisRole.setRoleName(roleDTO.getRoleName());
				roles.add(thisRole);								
			}
			
		}
				
		employee.setId(employeeDTO.getId());		
		employee.setName(nameInfo);
		employee.setAddress(addressInfo);
		employee.setOtherInfo(otherInfo);
		employee.setRole(roles);
		employee.setContacts(contacts);
				
		return employee;
	}
	
	public EmployeeDTO toDTO(Employee employee){
		EmployeeDTO employeeDTO = new EmployeeDTO();
		NameDTO nameInfoDTO = new NameDTO();
		AddressDTO addressInfoDTO = new AddressDTO();
		OtherInfoDTO otherInfoDTO = new OtherInfoDTO();
		Set<ContactDTO> contactsDTO = new HashSet();
		Set<RoleDTO> rolesDTO = new HashSet();
		
		Name name = employee.getName();
		nameInfoDTO.setTitle(name.getTitle());
		nameInfoDTO.setFirstName(name.getFirstName());
		nameInfoDTO.setMiddleName(name.getMiddleName());
		nameInfoDTO.setLastName(name.getLastName());
		nameInfoDTO.setSuffix(name.getSuffix());

		Address address = employee.getAddress();
		addressInfoDTO.setStreetNo(address.getStreetNo());
		addressInfoDTO.setBrgy(address.getBrgy());
		addressInfoDTO.setCityMunicipality(address.getCityMunicipality());
		addressInfoDTO.setZipcode(address.getZipcode());
		
		OtherInfo otherInfo = employee.getOtherInfo();
		otherInfoDTO.setBirthday(otherInfo.getBirthday());
		otherInfoDTO.setHireDate(otherInfo.getHireDate());
		otherInfoDTO.setGwa(otherInfo.getGwa());
		otherInfoDTO.setIsEmployed(otherInfo.getIsEmployed());
		
		if(employee.getContacts() != null){
			for(Contact contact: employee.getContacts()){
				ContactDTO thisContactDTO = new ContactDTO();
				thisContactDTO.setId(contact.getId());
				thisContactDTO.setContactDetails(contact.getContactDetails());
				thisContactDTO.setContactType(contact.getContactType());
				contactsDTO.add(thisContactDTO);
			}
		}
		
		if(employee.getRole() != null){
			for(Role role: employee.getRole()){
				RoleDTO thisRoleDTO = new RoleDTO();
				thisRoleDTO.setId(role.getId());
				thisRoleDTO.setRoleName(role.getRoleName());
				rolesDTO.add(thisRoleDTO);
			}	
		}		
		
		employeeDTO.setId(employee.getId());
		employeeDTO.setName(nameInfoDTO);
		employeeDTO.setAddress(addressInfoDTO);
		employeeDTO.setOtherInfo(otherInfoDTO);
		employeeDTO.setContacts(contactsDTO);
		employeeDTO.setRole(rolesDTO);
		
		return employeeDTO;
	}
}
