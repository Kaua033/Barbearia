package Minha.Barbearia.OpenIAdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenIaDOC {

    @Bean
public OpenAPI apiInfo(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Uma API De barbearia")
                                .description("Um CRUD basico")
                                .version("1.0.0")
                );

}

}
