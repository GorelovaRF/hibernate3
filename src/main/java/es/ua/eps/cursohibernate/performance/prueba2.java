package es.ua.eps.cursohibernate.performance;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.ua.eps.cursohibernate.model.Conexion;
import es.ua.eps.cursohibernate.model.Usuario;
import es.ua.eps.cursohibernate.util.HibernateUtil;

public class prueba2 {
	
	public static void main (String [] args) {
		prueba2.getU();
		//prueba2.getSQL();
		prueba2.getJoinFetch();
		
	}


		

	private static void getSQL() {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada SQL.");		
		session.beginTransaction();
		
		
		String sql="SELECT * FROM dbo.usuario WHERE id_usuario BETWEEN 10 AND 20";
			
		org.hibernate.query.Query query = session.createNativeQuery(sql,Usuario.class);
		List<Usuario> listU = query.list();
	
		for (Usuario usuario : listU) {
			//System.out.println("Usuario: " + usuario.getId_usuario());
			for(Conexion c: usuario.getConexionset()) { 
				c.getMomento_entrada();
			}
			} 
		        
 	
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close(); 
		
		
	}




	private static void getJoinFetch() {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada. Query with JOIN");		
		session.beginTransaction();
		
		String hqlJoin="select u from Usuario u INNER JOIN FETCH u.conexionset where u.id_usuario between 10 and 20 ";
			
		org.hibernate.query.Query queryJoin = session.createQuery(hqlJoin,Usuario.class);
		List<Usuario> listJoin = queryJoin.list();
		for (Usuario usuario : listJoin) {
			System.out.println(usuario.getConexionset());
		}
			
		      	
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close(); 
		
		
		
	}




	private static void getU() {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada.");		
		session.beginTransaction();
		
		String hql="select u from Usuario u where u.id_usuario between 10 and 20 ";
		
			
		org.hibernate.query.Query query = session.createQuery(hql,Usuario.class);
		List<Usuario> listU = query.list();
	
		for (Usuario usuario : listU) {
			//System.out.println("Usuario: " + usuario.getId_usuario());
			for(Conexion c: usuario.getConexionset()) { 
				c.getMomento_entrada();
			}
			} 
		        
 	
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close(); 
		
	}

}
