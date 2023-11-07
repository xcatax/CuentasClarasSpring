package ttps.spring.DAO;

import ttps.spring.model.*;

public interface GrupoDAO extends GenericDAO<Grupo> {

	public Grupo buscarGrupoPorNombre(String nombre);
}
