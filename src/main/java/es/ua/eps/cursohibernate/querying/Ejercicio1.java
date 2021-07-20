package es.ua.eps.cursohibernate.querying;



import java.util.Set;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import org.hibernate.Session;
import es.ua.eps.cursohibernate.model.Usuario;
import es.ua.eps.cursohibernate.model.Conexion;
import es.ua.eps.cursohibernate.util.HibernateUtil;

 public class Ejercicio1 {
	 	 
	static int id_usuario = 11001;
	
	public static void main (String [] args) {
				
		Ejercicio1.getConect1();
		Ejercicio1.getConect2();
		Ejercicio1.getConect3();
		Ejercicio1.getConect4();
		Ejercicio1.getConect5();
		Ejercicio1.getConect6();
		Ejercicio1.getConect7();
		
		
	}
		
	
	private static void getConect1 () {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		session.beginTransaction();
		
		Usuario user = session.get(Usuario.class, id_usuario);		
		Set<Conexion> conexiones = user.getConexionset();
        for(Conexion conexion : conexiones)
        {
            System.out.println("1.Inf obtenida mediante get:  " + conexion.getMomento_entrada().toString());
        }
					
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close(); 
	}  
	
	private static void getConect2() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		session.beginTransaction();	
		
		List<Object> listconexiones = new ArrayList<Object>();		
		listconexiones = session.createNativeQuery("SELECT momento_entrada FROM dbo.conexion WHERE id_usuario = 11001 ORDER BY momento_entrada DESC ").list();
		for (Object conexion : listconexiones) {		
			System.out.println("2.Inf obtenida mediante consulta nativa: "+conexion);	
		}	
			
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close(); 
			
	}
	
	private static void getConect3() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		session.beginTransaction();	
		
		List<Conexion> listconexiones = new ArrayList<Conexion>();	
		listconexiones = session.createNativeQuery("SELECT * FROM dbo.conexion WHERE momento_entrada BETWEEN '1965-01-01' AND '2019-12-31' AND id_usuario = 11001 ", Conexion.class).list();
		for (Conexion conexion : listconexiones) {
	
			System.out.println("3.Inf obtenida mediante consulta nativa, a travez de la entidad: "+conexion.getMomento_entrada());				
		}
		
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close(); 		
	}
	
	private static void getConect4() {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		session.beginTransaction();	
		
		
		org.hibernate.query.Query<Date> query = session.createQuery("SELECT momento_entrada FROM Conexion WHERE id_usuario =:id_usuario ORDER BY momento_entrada DESC").setParameter("id_usuario",id_usuario);
		List<Date> listconexiones = query.list();
		for(Date conexion : listconexiones) {
			System.out.println("4.Inf obtenida mediante HQL:  " + conexion);
		}				
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close();		
	}
	
	private static void getConect5() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		session.beginTransaction();	
		
		
	/*	org.hibernate.query.Query query = session.createQuery("select c from Conexion c where c.momento_entrada > :timestamp").setParameter("timestamp","1966-01-29",TemporalType.TIMESTAMP);
		
		List<Conexion> listconexiones = query.list();
		
	// Exception in thread "main" java.lang.ClassCastException: class java.lang.String cannot be cast to class java.util.Date (java.lang.String and java.util.Date are in module java.base of loader 'bootstrap')
		
	*/
		
		String hql = "SELECT c FROM Conexion c WHERE id_usuario = :id_usuario AND momento_entrada > '1966-01-29'";
		
		org.hibernate.query.Query query = session.createQuery(hql).setParameter("id_usuario",id_usuario);
			
		List<Conexion> listconexiones  = query.list();
		
		
		for( Conexion conexion : listconexiones)
		{
			System.out.println("5.Inf obtenida mediante HQL, objetos: " + conexion.getMomento_entrada());
		}  
					
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close();
		
	}
	
	
	private static void getConect6() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		session.beginTransaction();	
		
		 CriteriaBuilder builder = session.getCriteriaBuilder();
	       CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
	       Root<Conexion> root = criteria.from(Conexion.class);
	       
	       Path<Long> idPath = root.get("usuario");
	       Path<Date> datePath = root.get("momento_entrada");
	       
	       criteria.multiselect(datePath);
	       criteria.where(builder.equal(root.get("usuario"), id_usuario));
	       
	       List<Tuple> tuples = session.createQuery(criteria).list();
	       
	       for(Tuple tuple : tuples)
	       {
	           System.out.println("6.Inf obtenida mediante HQL criteria:  " + tuple.get(datePath));      
	       }	
		
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close();
	}
	
	private static void getConect7() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		session.beginTransaction();	
		
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
	       CriteriaQuery<Conexion> criteria = builder.createQuery(Conexion.class);
	       Root<Conexion> root = criteria.from(Conexion.class);
	       criteria.select(root);
	       criteria.where(builder.equal(root.get("usuario"), id_usuario));
	       
	       List<Conexion> conexiones = session.createQuery(criteria).list();
	       
	       for(Conexion conexion : conexiones)
	       {
	          System.out.println("7.Inf obtenida mediante HQL criteria, objetos:  " + conexion.getMomento_entrada());     
	       }
		
		
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close();
		
	}
	
	
	
	
	
	
	
	
	

	
}


	

	

