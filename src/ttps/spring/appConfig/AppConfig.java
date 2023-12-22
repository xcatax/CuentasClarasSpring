package ttps.spring.appConfig;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered; // Añade esta importación
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebMvc
@EnableJpaRepositories(basePackages = "ttps.spring.repository")
@ComponentScan(basePackages = "ttps.spring")
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                .allowCredentials(true)
                .maxAge(3600)
                .exposedHeaders("Access-Control-Allow-Origin"); // Agrega esta línea
    	
    // Agregar una excepción específica para el endpoint de login
        registry.addMapping("/login")
            .allowedOriginPatterns("*")
            .allowedMethods("POST")  // Ajusta según tus necesidades
            .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
            .allowCredentials(true)
            .maxAge(3600)
            .exposedHeaders("Access-Control-Allow-Origin");
    	}
    
    }