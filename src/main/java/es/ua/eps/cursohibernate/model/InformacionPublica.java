package es.ua.eps.cursohibernate.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Id;
import javax.persistence.Cacheable;
//import javax.persistence.GenerationType;
//import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

import java.io.Serializable;


@Entity
@Table(name="informacion_publica")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="informacion_publica")
public class InformacionPublica implements Serializable{
	
	/*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario", unique = true, nullable = false)
    private int id_usuario; */
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="mostrar_email", nullable = true)
    private boolean mostrar_email;
	
	@Column(name="mostrar_nacido", nullable = true )
    private boolean mostrar_nacido;
	
	@Column(name="mostrar_nombre", nullable = true)
    private boolean mostrar_nombre;
	
	//1:1
	
	@OneToOne
	@Id
	@JoinColumn (name="id_usuario", nullable = false)
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	//1:1
	
	/*public int getId_usuario() {
    	return id_usuario;
    }
    public void setId_perfil(int id_usuario) {
    	this.id_usuario=id_usuario;
    } */
    
    
    public boolean getMostrar_email() {
    	return mostrar_email;
    }
    public void setMostrar_email(boolean mostrar_email) {
    	this.mostrar_email=mostrar_email;
    }
    
    
    public boolean getMostrar_nacido() {
    	return mostrar_nacido;
    }
    public void setMostrar_nacido(boolean mostrar_nacido) {
    	this.mostrar_nacido=mostrar_nacido;
    }
    
    public boolean getMostrar_nombre() {
    	return mostrar_nombre;
    }
    public void setMostrar_nombre(boolean mostrar_nombre) {
    	this.mostrar_nombre=mostrar_nombre;
    }
    

}
