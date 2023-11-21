package ttps.spring.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ttps.spring.model.CategoriaGrupo;
import ttps.spring.model.Usuario;

@Repository
public interface CategoriaGrupoRepository extends JpaRepository<CategoriaGrupo, Long> {
	
	List<CategoriaGrupo> findAll(); 
	
	CategoriaGrupo save(CategoriaGrupo categoriaGrupo);
	    
	CategoriaGrupo findByNombre(String nombre);


}