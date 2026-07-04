package custompage.config.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFound_deberiaRetornar404ConMensaje() {
        ResourceNotFoundException ex = new ResourceNotFoundException("No se encontró la empresa 99");

        ResponseEntity<Object> response = handler.handleNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertThat(body.get("message")).isEqualTo("No se encontró la empresa 99");
    }

    @Test
    void handleGlobal_deberiaRetornar500ConError() {
        RuntimeException ex = new RuntimeException("Fallo inesperado");

        ResponseEntity<Object> response = handler.handleGlobal(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertThat(body.get("error")).isEqualTo("Fallo inesperado");
    }
}