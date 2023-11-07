package ttps.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="saldo")

public class Saldo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column
	private float debe; 
	@Column
	private boolean pagado ;
	
	@OneToOne
	private Usuario usuarioOrigen; 
	@OneToOne
	private Usuario usuarioDestino;
	@OneToOne
	private Gasto consumo;
	
	
	public float getDebe() {
		return debe;
	}
	public void setDebe(float debe) {
		this.debe = debe;
	}
	public Usuario getUsuarioOrigen() {
		return usuarioOrigen;
	}
	public void setUsuarioOrigen(Usuario usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}
	public Usuario getUsuarioDestino() {
		return usuarioDestino;
	}
	public void setUsuarioDestino(Usuario usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}
	public Gasto getConsumo() {
		return consumo;
	}
	public void setConsumo(Gasto consumo) {
		this.consumo = consumo;
	}  
	public boolean isPagado() {
		return pagado;
	}
	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}
	
	//Metodos
	
	 public void registrarSaldo(float debe, Usuario usuarioOrigen, Usuario usuarioDestino, Gasto consumo) {}
	 
	 public void registrarPago() {} // marca como pagado

}
