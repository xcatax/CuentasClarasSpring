package inicio;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestSpringSimple {
	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

		// registra una o más componentes que serán procesadas
		ctx.register(ttps.spring.config.PersistenceConfig.class);
		
		// Busca clases anotadas en el paquete base pasado por parámetro
		ctx.scan("ttps.spring.model","ttps.spring.controller");
		
		ctx.refresh();
		
		ctx.close();
	}
}
