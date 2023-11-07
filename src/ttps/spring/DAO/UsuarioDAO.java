package ttps.spring.DAO;

import ttps.spring.model.Usuario;
import ttps.spring.DAO.*;

public interface UsuarioDAO extends GenericDAO<Usuario> {

	//podriamos tener un get id!!
	public Usuario buscarUsuarioPorMail(String email);
}
