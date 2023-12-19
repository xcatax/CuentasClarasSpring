package ttps.spring.controller;

import ttps.spring.model.*;
import ttps.spring.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType; // Asegúrate de importar MediaType

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin(origins = "http://localhost:4200") 
@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	
	@PostMapping("/login")
	
	/*
	 * En el header enviar usuario: unUsr clave: unaClave, 
	 * si el par es correcto devuelve 200 con un header token: 
	 * {idUsuario}+’123456’ y en caso contrario 403.
	 * 
	 * */
/*	  public ResponseEntity<String> login(@RequestHeader("usuario") String usuario, @RequestHeader("clave") String clave) {
	    System.out.println("usuario por header: "+ usuario );
	    System.out.println("clave por header: "+ clave );
	  
	    //busco en la bd un usuario con esa clave y ese nombre de usuario
	    Usuario usuarioOk=usuarioRepository.findByNombreUsuarioAndContrasena(usuario, clave);
	    if (usuarioOk != null) {
	    	 //creo el token que hay qye mandar a la salida
	        String token = usuarioOk.getId() + "123456";
		    HttpHeaders headers = new HttpHeaders();
	        headers.add("token", token);

	        // Devolver respuesta exitosa (código 200) con el token en el header
	        return new ResponseEntity<String>("Autenticación exitosa", headers, HttpStatus.OK);
	    }else {
	    	return new ResponseEntity<String>("Autenticación fallida", HttpStatus.FORBIDDEN);
	    }
	  }
	*/
	 public ResponseEntity<Usuario> login(@RequestHeader("usuario") String usuario, @RequestHeader("clave") String clave) {
		    System.out.println("usuario por header: "+ usuario );
		    System.out.println("clave por header: "+ clave );
		  
		    //busco en la bd un usuario con esa clave y ese nombre de usuario
		    Usuario usuarioOk=usuarioRepository.findByNombreUsuarioAndContrasena(usuario, clave);
		    if (usuarioOk != null) {
		    	 //creo el token que hay qye mandar a la salida
		        String token = usuarioOk.getId() + "123456";
			    HttpHeaders headers = new HttpHeaders();
		        headers.add("token", token);
		     
		        
		        // Devolver respuesta exitosa (código 200) con el token en el header
		        return new ResponseEntity<Usuario>(usuarioOk, HttpStatus.OK);
		    }else {
		    	return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		    }
		  }
	
	@PostMapping("")
	@Transactional
	public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuarioParams) {
		System.out.println("Registro");
		if (usuarioParams == null) {
			System.out.println("No se han proporcionado parámetros");
			String message = "No se han proporcionado parámetros";
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}
		// System.out.println("Creando el usuario " + usuario.getEmail());
		Usuario usu = usuarioRepository.findByEmail(usuarioParams.getEmail());
		if (usu != null) {
			System.out.println("Ya existe un usuario con email " + usuarioParams.getEmail());
			//String message = "Ya existe un usuario con nombre ";
			return new ResponseEntity<Usuario>( HttpStatus.CONFLICT);
		}
		Usuario usuNuevo= usuarioRepository.save(usuarioParams);
		//String message = "Se guardó el usuario con éxito";
		return new ResponseEntity<Usuario>(usuNuevo, HttpStatus.CREATED);
	}
	
	
	
	
	
/*	public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
		System.out.println("Registro");
		if (usuario == null) {
			System.out.println("No se han proporcionado parámetros");
			String message = "No se han proporcionado parámetros";
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		// System.out.println("Creando el usuario " + usuario.getEmail());

		if (usuarioRepository.existsByEmail(usuario.getEmail())) {
			System.out.println("Ya existe un usuario con email " + usuario.getEmail());
			String message = "Ya existe un usuario con nombre ";
			return new ResponseEntity<>(message, HttpStatus.CONFLICT);
		}
		usuarioRepository.save(usuario);
		String message = "Se guardó el usuario con éxito";
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}*/

	@GetMapping("/listarTodos")
	 public ResponseEntity<List<Usuario>> listarUsuarios() {
        System.out.println("listar");
        List<Usuario> usuarios = usuarioRepository.findAll(); // Obtener todos los usuarios del repositorio
        return ResponseEntity.ok(usuarios); // Retornar una respuesta con la lista de usuarios
    }
		//return usuarioRepository.findAll();
		// return null;
	

	@GetMapping("/{id}")
	/*
	 * En el header enviar token: {idUsuario}+’123456’ #200 con los datos del
	 * usuario #404 si no se encuentra el usuario y #401 en caso de token inválido.
	 **/

	public ResponseEntity<Usuario> getUser(@PathVariable("id") long id, @RequestHeader(name = "token") String token) {

		System.out.println("Obteniendo un usuario con id" + id);
		String tokenEsperado = id + "123456";
		System.out.println("token esperado: " + tokenEsperado + "token que llega por parametro: " + token);

		if (tokenEsperado.equals(token)) { // token ok
			Usuario usu = usuarioRepository.findById(id);
			if (usu != null) { // existe el usuario
				return ResponseEntity.status(HttpStatus.OK).body(usu);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@GetMapping("/hello")
	public ResponseEntity<String> sayHello() {
		System.out.println("entre");
		String message = "Hola Mundo desde el controlador UserController";
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
