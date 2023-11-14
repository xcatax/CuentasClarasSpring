package ttps.spring.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ttps.spring.DAO.*;
import ttps.spring.model.*;

@Repository
public class GrupoDAOimpl extends GenericDAOimpl<Grupo> implements GrupoDAO{
		
		public GrupoDAOimpl() {
			super(Grupo.class); //setea la entidad que va a manejar esta implementacion
		}
		
		@Override
		public Grupo buscarGrupoPorNombre(String nombre) {
			try { 
				Query consulta = getEntityManager().createQuery("select g from Grupo g where g.nombre =:nombre");
				consulta.setParameter("nombre", nombre);
				return (Grupo)consulta.getSingleResult();
				
			} catch (NoResultException e) {
				//En el caso de que no se encuentre ningun usuario para ese mail retorna null
				return null;
			}
		}
		

	
}
