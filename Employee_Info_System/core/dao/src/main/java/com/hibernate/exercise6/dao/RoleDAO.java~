package com.hibernate.exercise6.dao;

import com.hibernate.exercise6.model.Role;
import com.hibernate.exercise6.model.Employee;
import com.hibernate.exercise6.utilities.HibernateUtil;

import com.hibernate.exercise6.dto.RoleDTO;
import com.hibernate.exercise6.dto.EmployeeDTO;

import com.hibernate.exercise6.dao.PersonDAO;

import java.util.Scanner;
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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RoleDAO{		
	private Scanner scan = new Scanner(System.in);
	private HibernateUtil hibernateUtil = new HibernateUtil();
	private List<Role> roleList;		
	private Role thisRole;
	private PersonDAO personDAO = new PersonDAO();
		
	public Role getRoleFromDB(int roleID){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();
			thisRole = (Role)session.get(Role.class, roleID);
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		return thisRole;
	}
	
	public void addNewRoleToDB(RoleDTO roleDTO){		
		SessionFactory factory = hibernateUtil.getSessionFactory();
		boolean loopRoleMenu = true;
		
		Session session = factory.openSession();
		Transaction transaction = null;
		try{
			transaction = session.beginTransaction();		
			Role role = roleToEntity(roleDTO);	
			session.save(role);
			transaction.commit();
			System.out.println("\tRole successfully added");
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}		
	}
	
	public void addRoleToEmployee(EmployeeDTO employeeDTO){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();		
			Employee employee = personDAO.toEntity(employeeDTO);	
			session.update(employee);
			transaction.commit();
			System.out.println("\t\tRole successfully added");
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}				
	}
	
	public void updateRoles(RoleDTO thisRoleDTO){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();	
			Role role = roleToEntity(thisRoleDTO);		
			session.update(role);
			transaction.commit();
			System.out.println("\t\tRole successfully updated");	
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}				
	}
	
	public void deleteRole(Role thisRole){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();			
			session.delete(thisRole);
			transaction.commit();			
			System.out.println("\tRole successfully deleted");
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}		
	}
	
	public void deleteRoleFromEmployee(EmployeeDTO employeeDTO){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();	
			Employee employee = personDAO.toEntity(employeeDTO);		
			session.update(employee);
			transaction.commit();
			System.out.println("\t\tRole success deleted");
				
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}		
	}
	
	public List<RoleDTO> getRoleList(){
		SessionFactory factory = hibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null;
		
		List<RoleDTO> roleListDTO = new ArrayList();
		
		try{
			transaction = session.beginTransaction();	
			roleList = session.createQuery("FROM Role").setCacheable(true).list();
			
			for(Role rol: roleList){
				roleListDTO.add(roleToDTO(rol));
			}
		}catch(HibernateException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return roleListDTO;		
	}
	
	public RoleDTO roleToDTO(Role rol){
		RoleDTO roleDTO = new RoleDTO();
		
		Set<EmployeeDTO> employeesDTO = new HashSet();
		
		Set<Employee> employees = rol.getEmployee();
		for(Iterator employeesIterator = employees.iterator(); employeesIterator.hasNext();){
			Employee employee = (Employee)employeesIterator.next();
			EmployeeDTO employeeDTO = new EmployeeDTO();
			
			employeeDTO = personDAO.toDTO(employee);
			employeesDTO.add(employeeDTO);			
		}
		
		roleDTO.setId(rol.getId());
		roleDTO.setRoleName(rol.getRoleName());
		roleDTO.setEmployee(employeesDTO);		
		
		return roleDTO;
	}
	
	public Role roleToEntity(RoleDTO roleDTO){
		Role role = new Role();
		
		Set<Employee> employees = new HashSet();
		
		if(roleDTO.getEmployee() != null){
			Set<EmployeeDTO> employeesDTO = roleDTO.getEmployee();
			for(Iterator employeesIteratorDTO = employeesDTO.iterator(); employeesIteratorDTO.hasNext();){
				EmployeeDTO employeeDTO = (EmployeeDTO)employeesIteratorDTO.next();
				Employee employee = new Employee();
			
				employee = personDAO.toEntity(employeeDTO);
				employees.add(employee);			
			}
			role.setEmployee(employees);		
		}			
		
		role.setId(roleDTO.getId());
		role.setRoleName(roleDTO.getRoleName());			
		
		return role;	
	}
}
