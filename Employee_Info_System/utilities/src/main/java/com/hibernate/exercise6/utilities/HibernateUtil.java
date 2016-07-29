package com.hibernate.exercise6.utilities;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;

import com.hibernate.exercise6.model.Employee;
import com.hibernate.exercise6.model.Name;
import com.hibernate.exercise6.model.Address;
import com.hibernate.exercise6.model.OtherInfo;
import com.hibernate.exercise6.model.Contact;
import com.hibernate.exercise6.model.Role;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil{
	
	private static AnnotationConfiguration cfg = new AnnotationConfiguration()
	.configure()
	.addAnnotatedClass(com.hibernate.exercise6.model.Employee.class)
	.addAnnotatedClass(com.hibernate.exercise6.model.Name.class)
	.addAnnotatedClass(com.hibernate.exercise6.model.Address.class)
	.addAnnotatedClass(com.hibernate.exercise6.model.OtherInfo.class)
	.addAnnotatedClass(com.hibernate.exercise6.model.Contact.class)
	.addAnnotatedClass(com.hibernate.exercise6.model.Role.class)
	.setProperty("hibernate.enable_lazy_load_no_trans","true")
	.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider")
	.setProperty("hibernate.cache.use_second_level_cache","true")
	.setProperty("hibernate.cache.use_query_cache","true")
	.setProperty("hibernate.cache.region.factory_class","org.hibernate.cache.ehcache.EhCacheRegionFactory");
	
	private static StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
	private static SessionFactory sessionFactory = cfg.buildSessionFactory(builder.build());
	//.buildSessionFactory();
			
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
