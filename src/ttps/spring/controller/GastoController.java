package ttps.spring.controller;

import ttps.spring.model.*;
import ttps.spring.repository.CategoriaGastoRepository;
import ttps.spring.repository.GastoRepository;
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
@RequestMapping(value = "/gastos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GastoController {

	@Autowired
	private GastoRepository gastoRepository;
	@Autowired
	private CategoriaGastoRepository categoriaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;

	
	@PostMapping("/crearGasto")
	@Transactional
	public ResponseEntity<String> crearGasto(@RequestBody Gasto gasto) {
	
		try {
			CategoriaGasto cat = categoriaRepository.findById(gasto.getCategoria().getId());
			if (cat == null) {
				String message = "No existe esa categoria";
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}
			gasto.setCategoria(cat);
			
			// Buscar usuario 
			Usuario usu = usuarioRepository.findById(gasto.getUsuarioOrigen().getId());
			if (usu == null) {
				String message = "No existe esa Usuario";
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}
			gasto.setUsuarioOrigen(usu);
			
			gastoRepository.save(gasto);
			String message = "Gasto guardado correctamente";
			return new ResponseEntity<>(message, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/listarTodos")
	public List<Gasto> gasto() {
		System.out.println("listar");
		return gastoRepository.findAll();
	}
	
	
	@PutMapping("/actualizarGasto/{id}")
	@Transactional
	public ResponseEntity<String> actualizarGasto(@PathVariable Long id, @RequestBody Gasto nuevoGasto) {
		        Gasto gastoExistente = gastoRepository.findById(id); //busco el grupo a modificar
		        String messageOk = "Se modifico: ";
				if(gastoExistente != null) { //Si lo encontre:
					//--------Modifica Nombre
					if(nuevoGasto.getNombre() != null) { // ingreso nombre?
						System.out.println("Intenta modificar el nombre");
						if (gastoRepository.findByNombre(nuevoGasto.getNombre()) != null) { //--- El nombre ya existe?
							String message = "Existe gasto con ese nombre";
							return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST); //corta
						}
						messageOk = messageOk+ "nombre, ";
				        System.out.println("El nombre esta ok se modifica");
				        gastoExistente.setNombre(nuevoGasto.getNombre());
					}
					
					//--------Modifica imagen 
					if(nuevoGasto.getImagen() != 0) {
				        System.out.println(" modifica imagen");
				        messageOk = messageOk+ " imagen,";
				        gastoExistente.setImagen(nuevoGasto.getImagen());
					}else {
				        System.out.println("IMAGEN vino null");
						}
					
					
					//--------Modifica categoria
					System.out.println(nuevoGasto.getCategoria());
					if(nuevoGasto.getCategoria() != null ) { //vino categoria para modificar
				        System.out.println("Intenta modificar categoria");
				        CategoriaGasto cat = categoriaRepository.findByNombre(nuevoGasto.getCategoria().getNombre());
						if (cat == null) { 
							String message = "No existe categoria con ese nombre";
							return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
						}else {
					    messageOk = messageOk+ " categoria,";
					    gastoExistente.setCategoria(cat);
						}
						
					}else {
				        System.out.println("categoria vino null");
					}
					//--------Modifica usuario
					System.out.println(nuevoGasto.getUsuarioOrigen());
					if(nuevoGasto.getUsuarioOrigen() != null ) { //vino categoria para modificar
				        System.out.println("Intenta modificar usuario");
				        Usuario usu = usuarioRepository.findByNombre(nuevoGasto.getUsuarioOrigen().getNombre());
						if (usu == null) { 
							String message = "No existe usuario con ese nombre";
							return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
						}else {
					    messageOk = messageOk+ " usuario";
					    gastoExistente.setUsuarioOrigen(usu);
						}
						
					}else {
				        System.out.println("usuario vino null");
						}
									
					
					return new ResponseEntity<>(messageOk, HttpStatus.OK);

				}else {
					String message = "No existe gasto con ese ID";
					return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
				} 
		}
}
				




