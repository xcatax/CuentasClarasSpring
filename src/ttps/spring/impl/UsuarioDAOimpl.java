package ttps.spring.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import ttps.spring.DAO.*;
import ttps.spring.model.*;

@Repository
public class UsuarioDAOimpl extends GenericDAOimpl<Usuario> implements UsuarioDAO{
		
		public UsuarioDAOimpl() {
			super(Usuario.class);
		}

		public Usuario buscarUsuarioPorMail(String email) {
			try { 
				Query consulta = getEntityManager().createQuery("select u from Usuario u where u.email =:email");
				consulta.setParameter("email", email);
				return (Usuario)consulta.getSingleResult();
				
			} catch (NoResultException e) {
				//En el caso de que no se encuentre ningun usuario para ese mail retorna null
				return null;
			}
		}
		
		
		public List<Grupo> obtenerGruposDelUsuarioPorId(long usuarioId) {
		    try {
		    	
		        Usuario usuario = getEntityManager().find(Usuario.class, usuarioId); // Buscar al usuario por su ID
		        List<Grupo> grupos = usuario.getGrupos(); // Obtener la lista de grupos del usuario
		        //getEntityManager().close(); supongo no va mas no?
		        return grupos;
		    } catch (Exception e) {
		    	System.out.println("EXCEPCION " + e);
		        e.printStackTrace();
		        return null; // Manejar cualquier excepción y devolver null si algo sale mal
		    }
		}
		
}
