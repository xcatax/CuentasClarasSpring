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
@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping("/registrarUsuario")
	@Transactional
	public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
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
	}

	@GetMapping("/listarTodos")
	public List<Usuario> usuario() {
		System.out.println("listar");
		return usuarioRepository.findAll();
		// return null;
	}

	@PostMapping("/loginUsuario")
	@Transactional
	public ResponseEntity<String> loginUsuario(@RequestBody Usuario usuario) {
		/*
		 * En el header enviar usuario: unUsr clave: unaClave, if (ok) -> devuelve 200
		 * con un header token: {idUsuario}+’123456’ else -> 403.
		 */
		System.out.println("Login");
		System.out.println("probando el exist");
		System.out.println(usuario.getNombre());

		/*
		 * if (usuarioRepository.existsByNombre(usuario.getNombre())) {
		 * System.out.println("Ya existe un usuario con nombre " + usuario.getNombre());
		 * return new ResponseEntity<>(HttpStatus.CONFLICT); }
		 */
		usuarioRepository.save(usuario);
		String message = "Se guardó el usuario con éxito";
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

	@GetMapping("/usuario/{id}")
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
