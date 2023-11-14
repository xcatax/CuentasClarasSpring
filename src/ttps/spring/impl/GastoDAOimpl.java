package ttps.spring.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ttps.spring.DAO.*;
import ttps.spring.model.*;

@Repository
public class GastoDAOimpl extends GenericDAOimpl<Gasto> implements GastoDAO{
		
		public GastoDAOimpl() {
			super(Gasto.class);
		}

		@Override
		public Gasto buscarGastoPorNombre(String nombre) {
			try { 
				Query consulta = this.getEntityManager().createQuery("select g from Gasto g where g.nombre =:nombre");
				return (Gasto)consulta.getSingleResult();
				
			} catch (NoResultException e) {
				//En el caso de que no se encuentre ningun usuario para ese mail retorna null
				return null;
			}
		}
}
