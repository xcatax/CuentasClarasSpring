package ttps.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ttps.spring.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
	
	List<Grupo> findAll(); 
	
	Grupo save(Grupo grupo);
	    
    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM Grupo g WHERE g.nombre = ?1")
    boolean existsByNombre(String nombre);

}