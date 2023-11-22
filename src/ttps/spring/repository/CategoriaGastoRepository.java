package ttps.spring.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ttps.spring.model.CategoriaGasto;
import ttps.spring.model.Grupo;


@Repository
public interface CategoriaGastoRepository extends JpaRepository<CategoriaGasto, Long> {
	
	List<CategoriaGasto> findAll(); 
	
	CategoriaGasto save(CategoriaGasto categoriaGrupo);
	    
	CategoriaGasto findByNombre(String nombre);
	
	CategoriaGasto findById (long id);

}