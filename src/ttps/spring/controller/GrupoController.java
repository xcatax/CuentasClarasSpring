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
	        Grupo grupoExistente = grupoRepository.findById(id); //busco el grupo a modificar
	        String messageOk = "Se modifico: ";
			if(grupoExistente != null) { //Si lo encontre:
				//--------Modifica Nombre
				if(nuevoGrupo.getNombre() != null) { // ingreso nombre?
					System.out.println("Intenta modificar el nombre");
					if (grupoRepository.findByNombre(nuevoGrupo.getNombre()) != null) { //--- El nombre ya existe?
						String message = "Existe grupo con ese nombre";
						return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST); //corta
					}
					messageOk = messageOk+ "nombre, ";
			        System.out.println("El nombre esta ok se modifica");
					grupoExistente.setNombre(nuevoGrupo.getNombre());
				}
				
				//--------Modifica imagen 
				if(nuevoGrupo.getImagen() != 0) {
			        System.out.println(" modifica imagen");
			        messageOk = messageOk+ " imagen,";
					grupoExistente.setImagen(nuevoGrupo.getImagen());
				}else {
			        System.out.println("IMAGEN vino null");
					}
				
				
				//--------Modifica categoria
				System.out.println(nuevoGrupo.getCategoria());
				if(nuevoGrupo.getCategoria() != null ) { //vino categoria para modificar
			        System.out.println("Intenta modificar categoria");
			        CategoriaGrupo cat = categoriaRepository.findByNombre(nuevoGrupo.getCategoria().getNombre());
					if (cat == null) { 
						String message = "No existe categoria con ese nombre";
						return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
					}else {
				    messageOk = messageOk+ " cateoria";
					grupoExistente.setCategoria(cat);
					}
					
				}else {
			        System.out.println("categoria vino null");
					}
								
				
				return new ResponseEntity<>(messageOk, HttpStatus.BAD_REQUEST);

			}else {
				String message = "No existe grupo con ese ID";
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			} 
	}
}
