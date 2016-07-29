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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RoleService{
	RoleDAO roleDAO = new RoleDAO();
	PersonDAO personDAO = new PersonDAO();
	private HibernateUtil hibernateUtil = new HibernateUtil();
	private Scanner scan = new Scanner(System.in);
	
	public boolean addNewRoleToDB(String roleName){
		boolean loopRoleMenu = true;
		
		RoleDTO roleDTO = new RoleDTO(roleName);		
		roleDAO.addNewRoleToDB(roleDTO);
		return loopRoleMenu;
	}
	
	public boolean deleteRoleFromDB(int roleID){
		boolean loopRoleMenu = true;
		
		Role thisRole = roleDAO.getRoleFromDB(roleID);				
		roleDAO.deleteRole(thisRole);
		return loopRoleMenu;
	}
	
	public boolean addRoleToEmployee(Integer employeeID, int roleID){
		boolean loopRoleMenu = true;
		
		Role thisRole = roleDAO.getRoleFromDB(roleID);
		RoleDTO thisRoleDTO = roleDAO.roleToDTO(thisRole);
		
		Employee employee = personDAO.getEmployeeRecord(employeeID);
		EmployeeDTO employeeDTO = personDAO.toDTO(employee);
		Set<RoleDTO> rolesDTO = employeeDTO.getRole();
		rolesDTO.add(thisRoleDTO);
		
		employeeDTO.setRole(rolesDTO);
		roleDAO.addRoleToEmployee(employeeDTO);	
		return loopRoleMenu;
	}
	
	public boolean deleteRoleFromEmployee(int employeeID, int roleID){
		boolean loopRoleMenu = true;
		
		Employee employee = personDAO.getEmployeeRecord(employeeID);
		EmployeeDTO employeeDTO = personDAO.toDTO(employee);
		
		Role thisRole = roleDAO.getRoleFromDB(roleID);
		RoleDTO thisRoleDTO = roleDAO.roleToDTO(thisRole);
		
		Set<RoleDTO> rolesDTO = employeeDTO.getRole();
		
		for(Iterator roleDTOIterator = rolesDTO.iterator(); roleDTOIterator.hasNext();){
			RoleDTO roleDTO = (RoleDTO)roleDTOIterator.next();
			if(thisRoleDTO.getId() == roleDTO.getId()){
				rolesDTO.remove(roleDTO);
			}
		}
		employeeDTO.setRole(rolesDTO);	
		roleDAO.deleteRoleFromEmployee(employeeDTO);			
		return loopRoleMenu;
	}
	
	public boolean switchUpdateRoles(int roleID, String roleName){
		boolean loopRecordMenu = true;
		
		Role thisRole = roleDAO.getRoleFromDB(roleID);
		RoleDTO thisRoleDTO = roleDAO.roleToDTO(thisRole);
		thisRoleDTO.setRoleName(roleName);	
		roleDAO.updateRoles(thisRoleDTO);
		return loopRecordMenu;		
	}
	
	public void displayEmployeeRole(Integer employeeID){
			Employee employee = personDAO.getEmployeeRecord(employeeID);
			EmployeeDTO employeeDTO = personDAO.toDTO(employee);
			
			Set<RoleDTO> rolesDTO = employeeDTO.getRole();			
			NameDTO nameDTO = employeeDTO.getName();			
			
			System.out.println("\n\t\tID   Name");
			System.out.println("\t\t" + employeeDTO.getId() + "  " + nameDTO.getFirstName() + " " + nameDTO.getMiddleName() + " " + nameDTO.getLastName());
			System.out.println("\n\t\tROLES");
			for(Iterator roleIteratorDTO = rolesDTO.iterator(); roleIteratorDTO.hasNext();){
				RoleDTO roleDTO = (RoleDTO)roleIteratorDTO.next();
				System.out.println("\t\t[" + roleDTO.getId() + "] " + roleDTO.getRoleName());
			}
	}
	
	public boolean roleList(){
		boolean loopRoleMenu = true;
		
		List<RoleDTO> roleListDTO = roleDAO.getRoleList();
		System.out.println("\n\t\tROLES");
		for(Iterator roleIteratorDTO = roleListDTO.iterator(); roleIteratorDTO.hasNext();){
				RoleDTO roleDTO = (RoleDTO)roleIteratorDTO.next();
			
				System.out.println("\t\t[" + roleDTO.getId() + "] " + roleDTO.getRoleName());
		}	
		
		return loopRoleMenu;			
	}
	
	public void roleList(List roleList){	
		for(Iterator roleIterator = roleList.iterator(); roleIterator.hasNext();){
				Role role = (Role)roleIterator.next();
		
				System.out.println("\t[" + role.getId() + "] " + role.getRoleName());
		}	
	}
	
	public boolean displayRoleEmployeesList(){
		boolean loopRoleMenu = true;
		
		List<RoleDTO> roleListDTO = roleDAO.getRoleList();
		System.out.println("");
		for(Iterator roleIteratorDTO = roleListDTO.iterator(); roleIteratorDTO.hasNext();){
			RoleDTO roleDTO = (RoleDTO)roleIteratorDTO.next();
			System.out.println("\t[" + roleDTO.getId() + "] " + roleDTO.getRoleName() + " ");
			Set<EmployeeDTO> employeesDTO = roleDTO.getEmployee();
			List<String> thisRoleEmployeesDTO = roleEmployeesToList(employeesDTO);
			for(Iterator employeeIteratorDTO = thisRoleEmployeesDTO.iterator(); employeeIteratorDTO.hasNext();){
				String employeeNameDTO = (String)employeeIteratorDTO.next();
			
				System.out.println("\t\t" + employeeNameDTO);
			}
			System.out.println("");
		}
		
		return loopRoleMenu;
	}
	
	public List<String> roleEmployeesToList(Set<EmployeeDTO> employees){
		List<String> thisRoleEmployeesDTO = new ArrayList();
		
		for(Iterator employeeIteratorDTO = employees.iterator(); employeeIteratorDTO.hasNext();){
			EmployeeDTO employeeDTO = (EmployeeDTO)employeeIteratorDTO.next();
			NameDTO nameDTO = employeeDTO.getName();
			thisRoleEmployeesDTO.add("[" + employeeDTO.getId() + "] " +nameDTO.getFirstName() + " " + nameDTO.getLastName());
		}
		
		return thisRoleEmployeesDTO;
	}		
}
