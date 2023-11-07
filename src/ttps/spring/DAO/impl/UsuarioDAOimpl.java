package ttps.spring.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import ttps.spring.model.*;
import ttps.spring.DAO.*;

public class UsuarioDAOimpl extends GenericDAOimpl<Usuario> implements UsuarioDAO{
		
		public UsuarioDAOimpl() {
			super(Usuario.class);
		}

		public Usuario buscarUsuarioPorMail(String email) {
			try { 
				Query consulta = Factory.getEntityManagerFactory().createEntityManager().
				createQuery("select u from Usuario u where u.email =:email");
				consulta.setParameter("email", email);
				return (Usuario)consulta.getSingleResult();
				
			} catch (NoResultException e) {
				//En el caso de que no se encuentre ningun usuario para ese mail retorna null
				return null;
			}
		}
		
		
		public List<Grupo> obtenerGruposDelUsuarioPorId(long usuarioId) {
		    try {
		    	

		        EntityManager em = Factory.getEntityManagerFactory().createEntityManager();
		        Usuario usuario = em.find(Usuario.class, usuarioId); // Buscar al usuario por su ID
		        List<Grupo> grupos = usuario.getGrupos(); // Obtener la lista de grupos del usuario
		        em.close();
		        return grupos;
		    } catch (Exception e) {
		    	System.out.println("EXCEPCION " + e);

		        e.printStackTrace();
		        return null; // Manejar cualquier excepción y devolver null si algo sale mal
		    }
		}
		
}
