package ttps.spring.DAO.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import DAO.GastoDAO;
import entidades.Gasto;
import entidades.Usuario;
import inicio.Factory;

public class GastoDAOimpl extends GenericDAOimpl<Gasto> implements GastoDAO{
		
		public GastoDAOimpl() {
			super(Gasto.class);
		}

		@Override
		public Gasto buscarGastoPorNombre(String nombre) {
			try { 
				Query consulta = Factory.getEntityManagerFactory().createEntityManager().
				createQuery("select g from Gasto g where g.nombre =:nombre");
				consulta.setParameter("nombre", nombre);
				return (Gasto)consulta.getSingleResult();
				
			} catch (NoResultException e) {
				//En el caso de que no se encuentre ningun usuario para ese mail retorna null
				return null;
			}
		}
}
