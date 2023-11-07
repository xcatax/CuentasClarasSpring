package ttps.spring.DAO.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import DAO.GenericDAO;
import entidades.Usuario;
import inicio.Factory;

public class GenericDAOimpl<T> implements GenericDAO<T> {

	protected Class<T> persistentClass;

	public GenericDAOimpl(Class<T> clase) {
		persistentClass = clase;
	}

	@Override
	public T guardar(T entidad) {
		// instancia de EntityManager
		EntityManager em = Factory.getEntityManagerFactory().createEntityManager();
		// variable para la transacción.
		EntityTransaction tx = null;
		try {
			// obtiene una transaccion
			tx = em.getTransaction();
			// inicia la transaccion
			tx.begin();
			// persiste la entidad
			em.persist(entidad);
			// confirma la tx y aplica los cambios en la bd
			tx.commit();
		} catch (RuntimeException e) {
			// En caso de excepcion verifica si la tx esta activa para hacer rollback
			if (tx != null && tx.isActive())
				tx.rollback();
			throw e;
		} finally {
			// cierra el entity manager
			em.close();
		}
		return entidad;
	}

	// pisa la otra!
	@Override
	public T actualizar(T entidad) {

		EntityManager em = Factory.getEntityManagerFactory().createEntityManager();

		EntityTransaction tx = em.getTransaction();

		tx.begin();

		T entityMerged = em.merge(entidad);

		tx.commit();

		em.close();

		return entityMerged;
	}

	@Override
	public void borrar(T entidad) {
		EntityManager em = Factory.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = null;

		try {
			tx = em.getTransaction();
			tx.begin();
			em.remove(em.merge(entidad));
			tx.commit();
		}

		catch (RuntimeException e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			throw e; // escribir en un log o mostrar un mensaje
		} finally {
			em.close();
		}
	}

	// este borrar que onda? lo necesitamos?
	@Override
	public void eliminar(long id) {
		EntityManager em = Factory.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = null;

		try {

			tx = em.getTransaction();
			tx.begin();
			Usuario entity = em.find(Usuario.class, id);
			if (entity != null) {
				em.remove(entity);
				tx.commit();

			}
		} catch (RuntimeException e) {

			if (tx != null && tx.isActive())
				tx.rollback();
			throw e;

		} finally {
			em.close();
		}
	}
	
	@Override
	public List<T> listar() {
		Query consulta= Factory.getEntityManagerFactory().createEntityManager().createQuery("select e from "+ persistentClass.getSimpleName()+ " e");
		return (List<T>)consulta.getResultList();
	
	}


}
