package Minha.Barbearia.SecurityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Configuração de CORS (Cross-Origin Resource Sharing).
 *
 * Permite que o frontend Angular (localhost:4200) e o deploy
 * no Render consumam a API sem serem bloqueados pelo navegador.
 */
@Configuration
public class Cors implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:4200",          // Frontend local (Angular)
                        "https://barbearia-4zni.onrender.com" // Deploy no Render
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
