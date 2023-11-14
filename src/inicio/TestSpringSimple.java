package inicio;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ttps.spring.model.*;
import ttps.spring.DAO.UsuarioDAO;


public class TestSpringSimple {
	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

		// registra una o más componentes que serán procesadas
		ctx.register(ttps.spring.config.PersistenceConfig.class);
		
		// Busca clases anotadas en el paquete base pasado por parámetro
		ctx.scan("ttps.spring.DAO","ttps.spring.impl","ttps.spring.model");
		
		ctx.refresh();
		
		Usuario usr = new Usuario("NombreUsuario", "AppelidoUsuario", "Username", "unMailDeUsuario@gmail.com", "unaPassword");
		UsuarioDAO usrDAO = ctx.getBean("usuarioDAOimpl", UsuarioDAO.class);
		usrDAO.guardar(usr);
		
		//System.out.println(usrDAO.listar());
		/*System.out.println("** Listar Usuarios despues de borrar");
			for (Usuario user: usrDAO.listar()) { // Va a imprimir todos los usuarios
					System.out.println(user.getNombreUsuario());
					}
		*/
		ctx.close();
	}
}
