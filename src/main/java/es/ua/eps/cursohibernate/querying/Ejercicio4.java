package es.ua.eps.cursohibernate.querying;



import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import es.ua.eps.cursohibernate.util.HibernateUtil;
import es.ua.eps.cursohibernate.model.Conexion;

public class Ejercicio4 {
	public static void main (String [] args) {
		
		Ejercicio4.getId();
		Ejercicio4.getPremium();
		Ejercicio4.getDelete();
		
	}

	

	private static void getId() {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		 session.beginTransaction();
		 
		Query query = session.getNamedQuery("selectId").setParameter("id_usuario", 11001);
		
		List<Date> dates = query.list();
		for (Date date : dates) {
		System.out.println("Usuario con id 11001: " + date);	
		}
		
			 
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close();	
		
		
	}
	

	private static void getPremium() {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		 session.beginTransaction();
		 
		Query query = session.getNamedQuery("selectPremium");
		query.setParameter("id_perfil", 2);
		
		List<Conexion> conexiones = query.list();
		for (Conexion conexion : conexiones) {
		System.out.println( "Conexiones de usuarios con perfil Premium:  " + conexion.getMomento_entrada());	
		}
		
			 
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close();	
		
		
		
	}
	
	
	private static void getDelete() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		 session.beginTransaction();
		 
		 org.hibernate.query.Query query = session.getNamedQuery("deleteRows").setParameter("id_perfil", 0);
			int deletedRows = query.executeUpdate();
			System.out.println("Se borraron: "+ deletedRows + "  perfiles");
		 
		 session.getTransaction().rollback();				
			HibernateUtil.getSessionFactory().close();  
			
			
			
		
	} 

}
