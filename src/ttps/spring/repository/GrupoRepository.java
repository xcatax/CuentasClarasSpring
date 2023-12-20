package ttps.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ttps.spring.model.CategoriaGrupo;
import ttps.spring.model.Grupo;
import ttps.spring.model.Gasto;


@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
	
	List<Grupo> findAll(); 
	Grupo findByNombre (String nombre);
	Grupo findById (long id);
	Grupo save(Grupo grupo);
	
    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM Grupo g WHERE g.nombre = ?1")
    boolean existsByNombre(String nombre);
    
  /*  @Query("SELECT g.nombre from Gasto g  WHERE g.grupo.id = ?1")
    List<Gasto> findAllGastos(long idGrupo);*/

    @Query("SELECT g FROM Gasto g WHERE g.grupo.id = ?1")
    List<Gasto> findAllGastos(long idGrupo);
}