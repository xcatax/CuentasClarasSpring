package ttps.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="grupo")
public class Grupo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "nombre")
    private String nombre;
	
	@Column(name = "imagen")
    private byte imagen;
	
	@OneToOne
	private CategoriaGrupo categoria; 
	
	 @JsonIgnore
	 @OneToMany(mappedBy = "grupo")
	    private List<Gasto> gastos = new ArrayList<>();
	
	
	@ManyToMany(fetch = FetchType.EAGER)

	@JoinTable(
		name = "integrantes_grupo", // Nombre de la tabla intermedia
		joinColumns = @JoinColumn(name = "grupo_id"), // Define el campo (columna) en la tabla intermedia que se utilizará como clave externa para la entidad Usuario
	    inverseJoinColumns = @JoinColumn(name = "usuario_id") // Define el campo (columba) en la tabla intermedia que se utilizará como clave externa para la entidad Grupo
		)	
	private List<Usuario> integrantes =new ArrayList<>();
	
	public Grupo( String nombre, byte imagen, CategoriaGrupo categoria, Usuario PrimerUsuario) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.categoria = categoria;
        this.integrantes.add(PrimerUsuario);
	}
       
	public Grupo( String nombre, byte imagen, Usuario PrimerUsuario) {
	        this.nombre = nombre;
	        this.imagen = imagen;
	    	this.integrantes.add(PrimerUsuario);

	    }
	
	public Grupo( String nombre, byte imagen, CategoriaGrupo categoria) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.categoria = categoria;
    }
       
	
	
	public long getId() {
		return id;
	}
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public CategoriaGrupo getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaGrupo categoria) {
		this.categoria = categoria;
	}
	public Grupo() {
	}

	public byte getImagen() {
		return imagen;
	}

	public void setImagen(byte imagen) {
		this.imagen = imagen;
	}
	
	
	//metodos
	public Grupo  altaGrupo (String nombre, List<Usuario> integrantes, CategoriaGrupo categoria) {
		return null;
	}
	
	public void agregarIntegrante(Usuario usuario) {
	    this.integrantes.add(usuario); // Agregar usuario al grupo
	   // usuario.getGrupos().add(this); // Agregar el grupo a la lista de grupos del usuario
	}
	
	public void cargarGasto(Gasto gasto) {
	    this.gastos.add(gasto); 
	}
	
	public List<Usuario> getIntegrantes() {
		return integrantes;
	}
	
	public List<Gasto> getGastos() {
		return gastos;
	}

	public void setGastos(List<Gasto> gastos) {
		this.gastos = gastos;
	}

}
