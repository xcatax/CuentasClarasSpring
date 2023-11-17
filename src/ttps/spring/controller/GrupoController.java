package ttps.spring.controller;

import ttps.spring.model.*;
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

	@PostMapping("/crearGrupo")
	@Transactional
	public ResponseEntity<String> crearGrupo(@RequestBody Grupo grupo) {

		System.out.println("Creando el grupo    " + grupo.getNombre());

		 if (grupoRepository.existsByNombre(grupo.getNombre())) {
			  System.out.println(" nombre del gurpo:  " + grupo.getNombre());
			  String message = "Ya existe un grupo con nombre ";
			  return new ResponseEntity<>(message,HttpStatus.CONFLICT); 
		  }
		 grupoRepository.save(grupo);
		String message = "Se guardó el grupo con éxito";
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

}

	/*@GetMapping("/listarTodos")
	public List<Usuario> usuario() {
		System.out.println("listar");
		return usuarioRepository.findAll();
		// return null;
	}

	@GetMapping("/hello")
	public ResponseEntity<String> sayHello() {
		System.out.println("entre");
		String message = "Hola Mundo desde el controlador UserController";
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@PostMapping("/loginUsuario")
	@Transactional
	public ResponseEntity<String> loginUsuario(@RequestBody Usuario usuario) {

		System.out.println("probando el exist" );
		System.out.println(usuario.getNombre());
		
	/*	 if (usuarioRepository.existsByNombre(usuario.getNombre())) {
			  System.out.println("Ya existe un usuario con nombre " + usuario.getNombre());
		  
			  return new ResponseEntity<>(HttpStatus.CONFLICT); 
		  }
		 
		usuarioRepository.save(usuario);
		String message = "Se guardó el usuario con éxito";
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}*/

	/*
	 * 
	 * public UsuarioRepository getUsuarioRepository() { return usuarioRepository; }
	 * 
	 * 
	 * public void setUserRepository(UsuarioRepository uRepository) {
	 * this.usuarioRepository = uRepository; }
	 */


