package ttps.spring.controller;

import ttps.spring.model.*;
import ttps.spring.repository.UsuarioRepository;

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
	@RequestMapping(value ="/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
	public class UsuarioController {
		
		@Autowired
		private UsuarioRepository usuarioRepository;

		@PostMapping("/altaUsuario")
		@Transactional
		public ResponseEntity<String> altaUsuario(@RequestBody Usuario usuario) {
			     
			     System.out.println("Creando el usuario" + usuario.getNombre());
			   
			    /*if (usuarioRepository.isUserExist(usuario)) {
			    	System.out.println("Ya existe un usuario con nombre " + usuario.getNombre());
			    
			    	return new ResponseEntity<Usuario>(HttpStatus.CONFLICT); //Código de respuesta 409
			    }*/
			    usuarioRepository.save(usuario);
			    String message= "Se guardó el usuario con éxito";
			    return new ResponseEntity<>(message, HttpStatus.CREATED);
			    }
		
		
		@GetMapping("/listarTodos")
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
	    
	    

/*

		public UsuarioRepository getUsuarioRepository() {
			return usuarioRepository;
		}


		public void setUserRepository(UsuarioRepository uRepository) {
			this.usuarioRepository = uRepository;
		}*/
	
 }

