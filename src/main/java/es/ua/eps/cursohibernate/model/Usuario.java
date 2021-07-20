package es.ua.eps.cursohibernate.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

import java.io.Serializable;
//import java.util.HashSet;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;


//import java.util.Set;






@Entity
@Table(name="usuario",uniqueConstraints={@UniqueConstraint(columnNames={"id_usuario"})})
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="usuario")
public class Usuario  implements Serializable {
   

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario", unique = true, nullable=false)
    private int id_usuario;
    
   
    
    @Column(name="password", nullable = true)
    private byte[] password;
    
    @Column(name="apodo", nullable = true)
    private String apodo;
    
    @Column(name="email")
    private String email;
    
    @Column(name="nombre", nullable = false)
    private String nombre;
    
    @Column(name="apellidos", nullable = false)
    private String apellidos;
    
    @Column(name="nacido", nullable = false)
    private java.util.Date nacido;
    
  /*  @Column(name="id_perfil", nullable = true)
    private short id_perfil;*/
    
    
    //with conexion
    @OneToMany (mappedBy="usuario",fetch=FetchType.LAZY)
    @Fetch (FetchMode.SUBSELECT)
    private Set<Conexion> conexionset;
    
    public Set<Conexion> getConexionset(){
    	return conexionset;
    }
    public void setConexionset(Set<Conexion> conexionset) {
    	this.conexionset = conexionset;
    }
    //
    
    //with perfil
    @ManyToOne 
    @JoinColumn (name="id_perfil", nullable= false)
    private Perfil perfil;
    
    public Perfil getPerfil() {
    	return perfil;
    }
    public void setPerfil(Perfil perfil) {
    	this.perfil = perfil;
    }
    
    //
    
    
    
    //1:1 with infPub
    
    @OneToOne(mappedBy = "usuario")
    private InformacionPublica informacionPublica;
    
    public InformacionPublica getInformacionPublica () {
    	return informacionPublica;
    }
    public void setInformacionPublica(InformacionPublica informacionPublica) {
    	this.informacionPublica=informacionPublica;
    } 
    
    
    //1:1
    
    /* // N:M with Sigue
    
    private Set<Usuario> followers;
    private Set<Usuario> following;
    
    public Usuario () {}
    public Usuario (String nombre) {
    	this.nombre = nombre;
    	this.followers = new HashSet<Usuario>();
    	this.following = new HashSet<Usuario>();
    }
    
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable (name="sigue",
    joinColumns = @JoinColumn (name="id_seguido"),
    inverseJoinColumns = @JoinColumn(name = "id_seguidor"))
    public Set<Usuario> getFollowers() {
    	return followers;
    }
    public void setFollowers (Set<Usuario> followers) {
    	this.followers = followers;
    }
    public void addFollower(Usuario follower) {
    	followers.add(follower);
    	follower.following.add(this);
    	}
    @ManyToMany(mappedBy = "followers")
    public Set<Usuario> getFollowing(){
    	return following;
    }
    public void setFollowing(Set<Usuario> following) {
    	this.following = following;
    }
    public void addFollowing(Usuario followed) {
    	followed.addFollower(this);
    	
    }
    // */
    
    // N:M with Sigue

    
    @ManyToMany (mappedBy = "seguidores")
    private Set<Usuario> seguidos;
    
   

    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable (name = "sigue", joinColumns = @JoinColumn (name="id_seguido"),
    inverseJoinColumns = @JoinColumn(name = "id_seguidor"))
    private Set<Usuario> seguidores;
    
    
    //
    
    public int getId_usuario() {
    	return id_usuario;
    }
    public void setId_usuario(int id_usuario) {
    	this.id_usuario=id_usuario;
    }
    
    public byte[] getPassword() {
    	return password;
    }
    public void setPassword(byte[] password) {
    	this.password=password;
    }
    
    public String getApodo() {
    	return apodo;
    }
    public void setApodo(String apodo) {
    	this.apodo=apodo;
    }
    
    public String getEmail() {
    	return apodo;
    }
    public void setEmail(String email) {
    	this.email=email;
    }
    
    public String getNombre() {
    	return nombre;
    }
    public void setNombre(String nombre) {
    	this.nombre=nombre;
    }
    
    public String getApellidos() {
    	return apellidos;
    }
    public void setApellidos(String apellidos) {
    	this.apellidos=apellidos;
    }
    
    
    public java.util.Date getNacido() {
    	return nacido;
    }
    public void setNacido(java.util.Date nacido) {
    	this.nacido=nacido;
    }
    
    
   
    
    

    
	public void setSiguea(Set<Usuario> followed) {
		// TODO Auto-generated method stub
		this.seguidos = followed;
		for (Usuario user : followed) {
			user.getSeguidores().add(this);
		}
		
		
	}
	private Set<Usuario> getSeguidores() {
		// TODO Auto-generated method stub
		return seguidores;
	}
    
    
   
    
}

