package es.ua.eps.cursohibernate.querying;

import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.Session;
import org.hibernate.query.Query;

import es.ua.eps.cursohibernate.model.Conexion;
import es.ua.eps.cursohibernate.model.Perfil;
import es.ua.eps.cursohibernate.model.Usuario;
import es.ua.eps.cursohibernate.util.HibernateUtil;

public class Ejercicio3 {
	
	public static void main (String [] args) {
		Ejercicio3.getHQL();
		Ejercicio3.getCriteria();
		
	}


	private static void getHQL() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		 session.beginTransaction();
		 
		 
		String hqlTotal = "select c from Conexion c";
		List<Conexion> conexionesTotal = session.createQuery(hqlTotal,Conexion.class).list();
		System.out.println("Todos perfiles  " + conexionesTotal.size());
			
		 
		String hql = "select c from Conexion c where c.usuario.id_usuario in(select u.id_usuario from Usuario u where u.perfil.id_perfil in(select p.id_perfil from Perfil p where p.id_perfil = :id_perfil))";
		List<Conexion> conexiones = session.createQuery(hql,Conexion.class).setParameter("id_perfil",0).list();
		for (Conexion conexion : conexiones) {
			System.out.println("Los perfiles basicos:  " + conexion.getMomento_entrada());
			
		}
		
		System.out.println("Perfiles basicos  " + conexiones.size());
		
		String hqlDelete = "delete from Conexion c where c.usuario.id_usuario in(select u.id_usuario from Usuario u where u.perfil.id_perfil in(select p.id_perfil from Perfil p where p.id_perfil = :id_perfil))";
		org.hibernate.query.Query query = session.createQuery(hqlDelete).setParameter("id_perfil", 0);
		int deletedRows = query.executeUpdate();
		System.out.println("Se borraron: "+ deletedRows + "  perfiles");
		
		
		String hqlTotalDespues = "select c from Conexion c";
		List<Conexion> conexionesTotalDespues = session.createQuery(hqlTotalDespues,Conexion.class).list();
		System.out.println("Todos perfiles despues de modificacion:  " + conexionesTotalDespues.size());
		
		
			session.getTransaction().rollback();				
			HibernateUtil.getSessionFactory().close(); 
	}


	private static void getCriteria() {
			
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Session factory creada");		
		 session.beginTransaction();
		 
		 
		 CriteriaBuilder builder1 = session.getCriteriaBuilder();
		 CriteriaDelete<Conexion> delete = builder1.createCriteriaDelete(Conexion.class);
		Root<Conexion> root1 = delete.from(Conexion.class);
		 
		 Subquery<Conexion> subquery = delete.subquery(Conexion.class);
		 Root<Conexion> root2=subquery.from(Conexion.class);
		 subquery.select(root2);		 
		 Join <Conexion,Usuario> joinConUsu1 = root2.join("usuario",JoinType.INNER);
		 Join <Usuario,Perfil> joinUsuPer1 = joinConUsu1.join("perfil",JoinType.INNER);		 
		subquery.where(builder1.equal(joinUsuPer1.get("id_perfil"), 0));
		 
	
		 delete.where(root1.in(subquery));
		 Query query = session.createQuery(delete);
		 query.executeUpdate();
		 
		 
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		 CriteriaQuery <Tuple> criteria = builder.createQuery(Tuple.class);
		 Root <Conexion> root = criteria.from(Conexion.class);
		 Join <Conexion,Usuario> joinConUsu = root.join("usuario",JoinType.INNER);
		 Join <Usuario,Perfil> joinUsuPer = joinConUsu.join("perfil",JoinType.INNER);
		 
		 Path<Date> momPath = root.get("momento_entrada");
		 Path<Integer> idPath = joinConUsu.get("id_usuario");
		 Path<Integer> perfilPath = joinUsuPer.get("id_perfil");
		 
		 criteria.multiselect(idPath,perfilPath,momPath);
		 criteria.where(builder.equal(joinUsuPer.get("id_perfil"), 0));
		 	 
		 List<Tuple> tuples = session.createQuery(criteria).list();
		 
		/* for(Tuple tuple : tuples) {
			
			 Integer perf = tuple.get(perfilPath);
			 Date date=tuple.get(momPath);			 
			 System.out.println("Tipo del perfil  " + perf + ",   " + "momento de entrada  " + date);			 
		 }; */
		 
		 System.out.println("Conexiones en total:  "+ tuples.size()); 
				
		session.getTransaction().rollback();				
		HibernateUtil.getSessionFactory().close(); 
	}

}
