package es.ua.eps.cursohibernate.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Id;
import javax.persistence.Cacheable;
//import javax.persistence.GenerationType;
//import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
//import java.util.Set;
import java.io.Serializable;

@Entity
@NamedQueries({
	@NamedQuery(name ="selectId",query = "SELECT momento_entrada FROM Conexion WHERE id_usuario =:id_usuario ORDER BY momento_entrada DESC"),
	@NamedQuery(name = "selectPremium", query ="select c from Conexion c where c.usuario.perfil.id_perfil = :id_perfil"),
	@NamedQuery(name = "deleteRows", query = "delete from Conexion c where c.usuario.id_usuario in(select u.id_usuario from Usuario u where u.perfil.id_perfil in(select p.id_perfil from Perfil p where p.id_perfil = :id_perfil))")
})

@Table(name="conexion")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="conexion")
public class Conexion implements Serializable {
		

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/*@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario", unique = true, nullable = false)
    private int Id_usuario; */
	
	





	@Id
	@Column(name="momento_entrada", nullable = false)
    private java.util.Date momento_entrada;
	
	
	@ManyToOne
	@JoinColumn(name="id_usuario", nullable = false)
	private Usuario usuario;
	

	public Usuario getUsuario() {
		return usuario;
	}
	public void setIdusuario(Usuario usuario) {
		this.usuario= usuario;
	}
	
	 /* //M:N
	@ManyToMany(mappedBy = "conexiones")
	private Set<Perfil> perfiles;
	
	public Set<Perfil> getPerfiles() {
		return perfiles;
	}
	public void setPerfiles (Set<Perfil> perfiles) {
		this.perfiles = perfiles;
	} 
	
	//M:N */
	
	 /* public int getIdusuario() {
    	return idUsuario;
    }
	 public void setIdusuario(int idUsuario) {
    	this.idUsuario=idUsuario;
    } */
	
	
    
    public java.util.Date getMomento_entrada() {
    	return momento_entrada;
    }
    public void setMomento_entrada(java.util.Date momento_entrada) {
    	this.momento_entrada=momento_entrada;
    }

}
