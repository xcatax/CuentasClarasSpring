package ttps.spring.controller;

import ttps.spring.model.*;
import ttps.spring.repository.CategoriaGrupoRepository;
import ttps.spring.repository.GastoRepository;
import ttps.spring.repository.GrupoRepository;
import ttps.spring.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType; // Asegúrate de importar MediaType
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin(origins = "http://localhost:4200") 
@RestController
@RequestMapping(value = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;
	@Autowired
	private CategoriaGrupoRepository categoriaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private GastoRepository gastoRepository;

	//@Query("SELECT g.nombre from Gasto g INNER JOIN Grupo gr ON g.grupoId = gr.id")
    //List<Gasto> findAllGastos();
	//get/grupos/{idGrupo}/gastos
	
	//obtenerGastos
	@GetMapping("{idGrupo}/gastos")
	public List<Gasto> obtenerGastos(@PathVariable Long idGrupo) {
		System.out.println("listar gastos");
	    Grupo grupo = grupoRepository.findById(idGrupo);
	    if(grupo != null) {
	    	System.out.println("grupo: " + grupo);
			List<Gasto> gastos = grupoRepository.findAllGastos(grupo.getId());			
			System.out.println("gastos: " + gastos);
			//return grupoRepository.findAllGastos(grupo.getId());
			 return gastos;	    
	    }
	    return null;
	}

	
	@Transactional //este deberia ser post post/{idGrupo}
	@PostMapping("/{idGrupo}")
	public ResponseEntity<Grupo> agregarGasto(@PathVariable Long idGrupo, @RequestBody Gasto nuevoGasto) {
	    System.out.println("ID del Grupo: " + idGrupo);
	    System.out.println("Datos del gasto: " + nuevoGasto.getNombre());

	    // Validar la existencia del grupo
	    Grupo grupo = grupoRepository.findById(idGrupo);
	    if (grupo == null) {
	        String message = "No existe grupo con ese ID";
	        return new ResponseEntity<Grupo>( HttpStatus.BAD_REQUEST);
	    }

	    // Validar la existencia del gasto
	    Gasto gasto = gastoRepository.findByNombre(nuevoGasto.getNombre());
	    System.out.println(gasto);
	    if (gasto == null) {
	        String message = "No existe gasto con ese nombre";
	        return new ResponseEntity<Grupo>( HttpStatus.BAD_REQUEST);
	    }
	    
	 // Validar si el gasto ya está en el grupo
	    if (grupo.getGastos().contains(gasto)) {  //gesGastos seguro hay que agregarlo 
	        String message = "El gasto ya pertenece a este grupo";
	        return new ResponseEntity<Grupo>( HttpStatus.BAD_REQUEST);
	    }
	    
	    System.out.println("existe vamos agregar gasto");

	    // Agregar el gasto al grupo
	    grupo.cargarGasto(gasto);
	    gasto.setGrupo(grupo);
	    Grupo g = grupoRepository.save(grupo);
	    gastoRepository.save(gasto);


	    String message = "Gasto agregado al grupo exitosamente";
	    return new ResponseEntity<Grupo>(g, HttpStatus.OK);
	}
	
	@GetMapping("/listarCategorias")
	public List<CategoriaGrupo> categoriaGrupo() {
		System.out.println("listar");
		return categoriaRepository.findAll();
	}
	
	@PostMapping()
	@Transactional
	public ResponseEntity<Grupo> crearGrupo(@RequestBody Grupo grupo) {
		try {

			// Verificar si ya existe un grupo con el mismo nombre
			if (grupoRepository.findByNombre(grupo.getNombre()) != null) {
				String message = "Existe grupo con ese nombre";
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			// Buscar la categoría
			CategoriaGrupo cat = categoriaRepository.findByNombre(grupo.getCategoria().getNombre());
			if (cat == null) {
				String message = "No existe categoria con ese nombre";
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			grupo.setCategoria(cat);
			Grupo grupoOk =	grupoRepository.save(grupo);
			return new ResponseEntity<Grupo>(grupoOk,HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Grupo>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listarTodos")
	public ResponseEntity<List<Grupo>> listarGrupos() {
	    System.out.println("listar");
	    List<Grupo> grupos = grupoRepository.findAll(); // Obtener todos los grupos del repositorio
	    return ResponseEntity.ok(grupos);
	}



	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Grupo> actualizarGrupo(@PathVariable Long id, @RequestBody Grupo nuevoGrupo) {
		Grupo grupoExistente = grupoRepository.findById(id); // busco el grupo a modificar
		String messageOk = "Se modifico: ";
		if (grupoExistente != null) {  // Si lo encontre:
			// --------Modifica Nombre
			/*if (nuevoGrupo.getNombre() != null) { // ingreso nombre?
				System.out.println("Intenta modificar el nombre");
				if (grupoRepository.findByNombre(nuevoGrupo.getNombre()) != null) { // --- El nombre ya existe?
					String message = "Existe grupo con ese nombre";
					return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST); // corta
				}
				messageOk = messageOk + "nombre, ";
				System.out.println("El nombre esta ok se modifica");*/
				grupoExistente.setNombre(nuevoGrupo.getNombre());
			

			// --------Modifica imagen
			if (nuevoGrupo.getImagen() != 0) {
				System.out.println(" modifica imagen");
				messageOk = messageOk + " imagen,";
				grupoExistente.setImagen(nuevoGrupo.getImagen());
			} else {
				System.out.println("IMAGEN vino null");
			}

			// --------Modifica categoria
			System.out.println(nuevoGrupo.getCategoria());
			if (nuevoGrupo.getCategoria() != null) { // vino categoria para modificar
				System.out.println("Intenta modificar categoria");
				CategoriaGrupo cat = categoriaRepository.findByNombre(nuevoGrupo.getCategoria().getNombre());
				if (cat == null) {
					String message = "No existe categoria con ese nombre";
					return new ResponseEntity<Grupo>(HttpStatus.BAD_REQUEST);
				} else {
					messageOk = messageOk + " cateoria";
					grupoExistente.setCategoria(cat);
				}

			} else {
				System.out.println("categoria vino null");
			}

			return new ResponseEntity<Grupo>(HttpStatus.OK);

		}else { 
			//String message = "No existe grupo con ese ID";
			return new ResponseEntity<Grupo>(HttpStatus.BAD_REQUEST);
		}
	}

	
	@Transactional
	@PutMapping("/{idGrupo}/usuario")
	public ResponseEntity<String> agregarIntegrantes(@PathVariable Long idGrupo, @RequestBody Usuario integrante) {
	    System.out.println("ID del Grupo: " + idGrupo);
	    System.out.println("Datos del Usuario: " + integrante.getNombre());

	    // Validar la existencia del grupo
	    Grupo grupo = grupoRepository.findById(idGrupo);
	    if (grupo == null) {
	        String message = "No existe grupo con ese ID";
	        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	    }

	    // Validar la existencia del usuario
	    Usuario usuario = usuarioRepository.findByNombre(integrante.getNombre());
	    System.out.println(usuario);
	    if (usuario == null) {
	        String message = "No existe usuario con ese nombre";
	        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	    }
	    
	 // Validar si el usuario ya está en el grupo
	    if (grupo.getIntegrantes().contains(usuario)) {
	        String message = "El usuario ya pertenece a este grupo";
	        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	    }
	    
	    System.out.println("existe vamos agregar integrante");

	    // Agregar el usuario al grupo
	    grupo.agregarIntegrante(usuario);
	    grupoRepository.save(grupo);

	    String message = "Usuario agregado al grupo exitosamente";
	    return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
