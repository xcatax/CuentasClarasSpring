package ttps.spring.DAO;

import ttps.spring.model.*;

public interface GastoDAO extends GenericDAO<Gasto>{
	
	public Gasto buscarGastoPorNombre(String nombre);
}
