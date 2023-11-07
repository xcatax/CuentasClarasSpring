package ttps.spring.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "gasto")
public class Gasto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	 
	@Column
	private String nombre; 
	
	@Column
	private Date fecha;
	
	@Column
	private double monto; 
	
	@Column
	private byte imagen;
	
	@OneToOne
	private CategoriaGasto categoria; 
	
	@OneToOne
	private Usuario usuarioOrigen;
		
	@ManyToMany
    @JoinTable(
        name = "integrantes_gasto",
        joinColumns = @JoinColumn(name = "gasto_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> integrantes= new ArrayList<>();
	
	
	
	
/*relacion uno a uno, un gasto pertenece a una sola categoria
 * @OneToOne	
 * private CategoriaGasto categoria;
	*/
/*
 * Relación uno a uno: Un gasto está asociado a un único usuario que lo genera.
 *  @OneToOne	
 *  private Usuario usuarioOrigen;
*/
	
/*
 * Un gasto puede tener uno o varios integrantes, y un integrante puede pertenecer a 0 o varios gastos.
 * ver como resolver en este caso
 * 	@ManyToMany
 * private List<Usuario> integrantes; //lista de usuarios, que puede ser un usuario individuales o miembros de un grupo (todos o menos)
*/
	
/**
 * Un gasto tiene una unica división.
 * @OneToOne
 * private Division division;
*/
	
	public Gasto() {
		
	}
	Date date = new Date();
	//constructor completo -> resto de paramtros , CategoriaGasto categoria, Usuario usuarioOrigen, List<Usuario> integrantes, Division division
	public Gasto(String nombre, double monto, CategoriaGasto categoria, Usuario usuarioOrigen, byte imagen) {
	    this.nombre = nombre;
	    this.fecha =  date; 
	    this.monto = monto;
	    this.imagen = imagen;	   
	    this.categoria = categoria;
	    this.usuarioOrigen = usuarioOrigen;
	    //this.division = division;	    
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/*
	public CategoriaGasto getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaGasto categoria) {
		this.categoria = categoria;
	}
	*/
	
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public byte getImagen() {
		return imagen;
	}
	public void setImagen(byte imagen) {
		this.imagen = imagen;
	}
	/*public List<Usuario> getIntegrantes() {
		return integrantes;
	}
	public void setIntegrantes(List<Usuario> integrantes) {
		this.integrantes = integrantes;
	}
	public Usuario getUsuarioOrigen() {
		return usuarioOrigen;
	}
	public void setUsuarioOrigen(Usuario usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}

	// metodos
	public Gasto altaGasto(String nombre, Date fecha, byte imagen, Usuario usuarioOrigen, CategoriaGasto categoria, List<Usuario> integrantes, float monto, Division division) {
	    return null; 
	}
*/	
	public void editarGasto(Gasto gasto, double nuevoMonto, Date nuevaFecha, byte nuevaImagen, Usuario nuevoIntegrante, Division nuevaFormaDivisión) {
	    // Actualiza los atributos del gasto Gasto con los nuevos valores proporcionados
	}
	
	public void altaSaldos() {
		//calcula los saldos individuales de los integrantes del gasto
		// según la estrategia de división y el monto. 
		// Relaciona los saldos calculados y los integrantes del gasto.
	}

	public void agregarIntegrante(Usuario usuario) {
	    this.integrantes.add(usuario); // Agregar usuario al grupo
	}
}


