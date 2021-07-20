package es.ua.eps.cursohibernate.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Cacheable;
import javax.persistence.Column;
/*import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;*/
import javax.persistence.OneToMany;



@Entity
@Table(name="PERFIL")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="perfil")
public class Perfil implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_perfil", unique = true, nullable = false)
    private int id_perfil;
	
	@Column(name="descripcion", nullable = true)
    private  String descripcion;
	
	/* // M:N
	
	@ManyToMany
	@JoinTable(name = "USUARIO" ,
				joinColumns = {@JoinColumn(name = "id_perfil") },
				inverseJoinColumns = {@JoinColumn(name = "id_usuario")})
	
	private Set<Conexion> conexiones;
	
	public Set<Conexion> getConexiones() {
		return conexiones;
	}
	
	public void setConexiones(Set<Conexion> conexiones) {
		this.conexiones = conexiones;
	}
		
	//M:N */
	
	//1:N 2
	@OneToMany(mappedBy="perfil")
    private Set<Usuario> usuarios;
	
	public Set<Usuario> getUsuarios() {
        return usuarios;
    }
	
	 public void setUsuarios(Set<Usuario> usuarios) {
	        this.usuarios = usuarios;
	    }
	    
	 //1:N2
	
	

	public int getId_perfil() {
    	return id_perfil;
    }
    public void setId_perfil(int id_perfil) {
    	this.id_perfil=id_perfil;
    }
    
    public String getDescripcion() {
    	return descripcion;
    }
    public void setDescripcion(String descripcion) {
    	this.descripcion=descripcion;
    	}

}
