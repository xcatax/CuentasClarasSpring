package ttps.spring.controller;

import ttps.spring.model.*;
import ttps.spring.model.CategoriaGrupo;
import ttps.spring.repository.CategoriaGrupoRepository;
import ttps.spring.repository.GrupoRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType; // Asegúrate de importar MediaType

@RestController
@RequestMapping(value = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;
	@Autowired
	private CategoriaGrupoRepository categoriaRepository;

	@PostMapping("/crearGrupo")
	@Transactional
	public ResponseEntity<String> crearGrupo(@RequestBody Grupo grupo) {
		try {
			
			// Verificar si ya existe un grupo con el mismo nombre
			if (grupoRepository.findByNombre(grupo.getNombre()) != null) {
				String message = "Existe grupo con ese nombre";
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}
			// Buscar la categoría
			CategoriaGrupo cat = categoriaRepository.findByNombre(grupo.getCategoria().getNombre());
			if (cat == null) {
				String message = "No existe categoria con ese nombre";
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}
			grupo.setCategoria(cat);
			grupoRepository.save(grupo);
			String message = "Grupo guardado correctamente";
			return new ResponseEntity<>(message, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listarTodos")
	public List<Grupo> grupo() {
		System.out.println("listar");
		return grupoRepository.findAll();
		// return null;
	}

	@PutMapping("/actualizarGrupo/{id}")
	@Transactional
	public ResponseEntity<String> actualizarGrupo(@PathVariable Long id, @RequestBody Grupo nuevoGrupo) {
	  //  try {
	        // Verificar si el grupo existe
	        Grupo grupoExistente = grupoRepository.findById(id);
			if(grupoExistente != null) {
				if(nuevoGrupo.getNombre() != null) {
					System.out.println("Modifica nombre");
					if (grupoRepository.findByNombre(nuevoGrupo.getNombre()) != null) {
						String message = "Existe grupo con ese nombre";
						return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
					}
					grupoExistente.setNombre(nuevoGrupo.getNombre());
				}
				if(nuevoGrupo.getImagen() != 0) {
			        System.out.println("Modifica imagen");
					grupoExistente.setImagen(nuevoGrupo.getImagen());
				}
				
				if(nuevoGrupo.getCategoria().getNombre() != null ) {
			        System.out.println("Modifica categoria");
			        CategoriaGrupo cat = categoriaRepository.findByNombre(nuevoGrupo.getCategoria().getNombre());
					if (cat == null) {
						String message = "No existe categoria con ese nombre";
						return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
					}
					grupoExistente.setCategoria(cat);
				}
				
		        grupoRepository.save(grupoExistente);

			}
	        


  /*

	        // Verificar si ya existe otro grupo con el nuevo nombre
	        //grupoRepository.findByNombreAndIdNot(nuevoGrupo.getNombre(), id).ifPresent(existingGroup -> {

	        // Actualizar los datos del grupo
	        grupoExistente.setNombre(nuevoGrupo.getNombre());
	        grupoExistente.setCategoria(nuevoGrupo.getCategoria());
	        grupoExistente.setImagen(nuevoGrupo.getImagen());

	        // Guardar el grupo actualizado
	        grupoRepository.save(grupoExistente);

	        String message = "Grupo actualizado correctamente";
	        return new ResponseEntity<>(message, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
	    }*/
			return null;
	}
}
