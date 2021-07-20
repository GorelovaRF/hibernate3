package es.ua.eps.cursohibernate.querying;


import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Join;
import javax.persistence.Tuple;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import es.ua.eps.cursohibernate.model.Conexion;
import es.ua.eps.cursohibernate.model.Usuario;
import es.ua.eps.cursohibernate.model.Perfil;
import es.ua.eps.cursohibernate.util.HibernateUtil;

public class Ejercicio2 {
	
	public static void main (String [] args) {
		
		Ejercicio2.getNative();
		Ejercicio2.getHQL();
		Ejercicio2.getCriteria();
		
	}

	private static void getNative() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		session.beginTransaction();
		
		String sql = "SELECT momento_entrada FROM dbo.conexion c INNER JOIN dbo.usuario u ON u.id_usuario = c.id_usuario WHERE u.id_perfil = 2";
		List<String> conexiones = session.createNativeQuery(sql).list();

		for (String conexion : conexiones) {
			System.out.println("Native query:  " + conexion);
		}
				
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close(); 
	}
	
	private static void getHQL() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		session.beginTransaction();
		
		//List<Conexion> listconexiones = new ArrayList<Conexion>();
		String hql = "select c from Conexion c where c.usuario.perfil.id_perfil = :id_perfil";		
		org.hibernate.query.Query query = session.createQuery(hql,Conexion.class).setParameter("id_perfil", 2);
		
		List<Conexion> listconexiones = query.list();
		
		for (Conexion conexion : listconexiones) {
			System.out.println("HQL: " + conexion.getMomento_entrada());
		}
				
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close(); 
		
	}
	
	private static void getCriteria() {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		session.beginTransaction();
		
		 CriteriaBuilder builder = session.getCriteriaBuilder();
		 CriteriaQuery <Tuple> criteria = builder.createQuery(Tuple.class);
		 Root <Conexion> root = criteria.from(Conexion.class);
		 Join <Conexion,Usuario> joinConUsu = root.join("usuario",JoinType.INNER);
		 Join <Usuario,Perfil> joinUsuPer = joinConUsu.join("perfil",JoinType.INNER);
		 
		 Path<Date> momPath = root.get("momento_entrada");
		 Path<Long> idPath = joinConUsu.get("id_usuario");
		 Path<Integer> perfilPath = joinUsuPer.get("id_perfil");
		 
		 criteria.multiselect(idPath,perfilPath,momPath);
		criteria.where(builder.equal(joinUsuPer.get("id_perfil"), 2));
		 
		 List<Tuple> tuples = session.createQuery(criteria).list();
		 for(Tuple tuple : tuples) {

			 Integer perf = tuple.get(perfilPath);
			 Date date=tuple.get(momPath);			 
			 System.out.println("Tipo del perfil  " + perf + ",   " + "momento de entrada  " + date);	
			 
			// System.out.println("Criteria: " + tuple.get(momPath));
			 
		 };
		 
		 	
		session.getTransaction().commit();				
		HibernateUtil.getSessionFactory().close(); 
		
		
	}
	
	
	

}
