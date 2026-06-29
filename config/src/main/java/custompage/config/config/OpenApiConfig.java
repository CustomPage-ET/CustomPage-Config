package custompage.config.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CustomPage - Microservicio de Configuración Visual (SaaS)")
                        .version("1.0.0")
                        .description("API para la personalización estética, bloques y maquetación web de las PYMEs"));
    }
}