package ttps.spring.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import ttps.spring.DAO.*;

@Transactional
public class GenericDAOimpl<T> implements GenericDAO<T> {

	private EntityManager entityManager;

	protected Class<T> persistentClass;

	public GenericDAOimpl(Class<T> clase) {
		persistentClass = clase;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public T guardar(T entidad) {
		this.getEntityManager().persist(entidad);
		return entidad;
	}

	@Override
	public T actualizar(T entidad) {
		this.getEntityManager().merge(entidad);
		return entidad;
	}

	@Override
	public void borrar(T entidad) {
		this.getEntityManager().remove(this.getEntityManager().merge(entidad));
	}

	@Override
	public void eliminar(long id) {
		T entity = this.getEntityManager().find(persistentClass, id);
		this.getEntityManager().remove(entity);
	}

	@Override
	public List<T> listar() {
		Query consulta = this.getEntityManager().createQuery("select e from " + persistentClass.getSimpleName() + " e");
		return (List<T>)consulta.getResultList();
	}

}
