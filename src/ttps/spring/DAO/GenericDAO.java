package ttps.spring.DAO;

import java.util.List;


public interface GenericDAO<T> {
	
	List<T> listar();
	
	public T guardar(T entidad);
	
	T actualizar(T entidad);

    void borrar(T entidad);
	
	void eliminar(long id);



}
