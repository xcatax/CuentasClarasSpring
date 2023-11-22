package ttps.spring.controller;

import ttps.spring.model.*;
import ttps.spring.model.CategoriaGasto;
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
			
			/*// Verificar si ya existe un gasto con el mismo nombre NO ME PARECIO NECESARIO PUES ES UN GASTO Y PUEDO PAGAR ALQUILER DOS VECES
			if (gastoRepository.findByNombre(gasto.getNombre()) != null) {
				String message = "Existe grupo con ese nombre";
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}*/
			// Buscar la categoría
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

	



}
