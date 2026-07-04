package custompage.config.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

    private final OpenApiConfig openApiConfig = new OpenApiConfig();

    @Test
    void customOpenAPI_noDeberiaSerNulo() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        assertThat(openAPI).isNotNull();
    }

    @Test
    void customOpenAPI_deberiaContenerInfoConfigurada() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        Info info = openAPI.getInfo();

        assertThat(info).isNotNull();
        assertThat(info.getTitle())
                .isEqualTo("CustomPage - Microservicio de Configuración Visual (SaaS)");
        assertThat(info.getVersion()).isEqualTo("1.0.0");
        assertThat(info.getDescription())
                .isEqualTo("API para la personalización estética, bloques y maquetación web de las PYMEs");
    }

    @Test
    void customOpenAPI_deberiaRetornarNuevaInstanciaCadaVez() {
        OpenAPI primera = openApiConfig.customOpenAPI();
        OpenAPI segunda = openApiConfig.customOpenAPI();

        assertThat(primera).isNotSameAs(segunda);
    }
}