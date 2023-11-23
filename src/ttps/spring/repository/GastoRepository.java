package ttps.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ttps.spring.model.Gasto;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
	
	List<Gasto> findAll(); 
	
	Gasto findByNombre (String nombre);
	
	Gasto findById (long id);


	Gasto save(Gasto gasto);
	    
    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM Gasto g WHERE g.nombre = ?1")
    boolean existsByNombre(String nombre);

}