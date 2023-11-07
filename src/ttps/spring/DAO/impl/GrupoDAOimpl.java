package ttps.spring.DAO.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityTransaction;
//import javax.persistence.Query;

import DAO.GrupoDAO;
import entidades.Gasto;
import entidades.Grupo;

import inicio.Factory;

public class GrupoDAOimpl extends GenericDAOimpl<Grupo> implements GrupoDAO{
		
		public GrupoDAOimpl() {
			super(Grupo.class); //setea la entidad que va a manejar esta implementacion
		}
		
		@Override
		public Grupo buscarGrupoPorNombre(String nombre) {
			try { 
				Query consulta = Factory.getEntityManagerFactory().createEntityManager().
				createQuery("select g from Grupo g where g.nombre =:nombre");
				consulta.setParameter("nombre", nombre);
				return (Grupo)consulta.getSingleResult();
				
			} catch (NoResultException e) {
				//En el caso de que no se encuentre ningun usuario para ese mail retorna null
				return null;
			}
		}
		

	
}
