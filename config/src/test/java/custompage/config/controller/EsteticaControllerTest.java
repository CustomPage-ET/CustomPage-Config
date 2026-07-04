package custompage.config.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import custompage.config.dto.EsteticaDTO;
import custompage.config.dto.ModuloDTO;
import custompage.config.dto.TiendaConfigCompletaDTO;
import custompage.config.exception.GlobalExceptionHandler;
import custompage.config.service.IConfigService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EsteticaControllerTest {

    @Mock
    private IConfigService service;

    @InjectMocks
    private EsteticaController controller;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    void guardarEstetica_deberiaRetornar200ConDtoGuardado() throws Exception {
        EsteticaDTO request = EsteticaDTO.builder()
                .idEmpresa(1L)
                .paletaColores("#FFFFFF")
                .fuenteTexto("Roboto")
                .urlLogo("http://logo.com/logo.png")
                .build();

        EsteticaDTO respuesta = EsteticaDTO.builder()
                .idEstetica(10L)
                .idEmpresa(1L)
                .paletaColores("#FFFFFF")
                .fuenteTexto("Roboto")
                .urlLogo("http://logo.com/logo.png")
                .build();

        when(service.guardarOActualizarEstetica(any(EsteticaDTO.class))).thenReturn(respuesta);

        mockMvc.perform(post("/api/config/estetica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEstetica").value(10))
                .andExpect(jsonPath("$.paletaColores").value("#FFFFFF"));
    }

    @Test
    void guardarEstetica_deberiaRetornar400SiFaltanCamposObligatorios() throws Exception {
        EsteticaDTO requestInvalido = EsteticaDTO.builder()
                .idEmpresa(1L)
                .build(); // faltan paletaColores, fuenteTexto, urlLogo

        mockMvc.perform(post("/api/config/estetica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerConfigTienda_deberiaRetornar200ConConfiguracionCompleta() throws Exception {
        TiendaConfigCompletaDTO respuesta = TiendaConfigCompletaDTO.builder()
                .idEmpresa(1L)
                .estetica(EsteticaDTO.builder().idEmpresa(1L).paletaColores("#000000")
                        .fuenteTexto("Arial").urlLogo("http://logo.com").build())
                .modulosActivos(List.of(
                        ModuloDTO.builder().idModulo(1L).idEmpresa(1L)
                                .nombreModulo("Reportes").activo(true).ordenPantalla(1).build()
                ))
                .build();

        when(service.obtenerConfiguracionTienda(1L)).thenReturn(respuesta);

        mockMvc.perform(get("/api/config/tienda/{idEmpresa}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEmpresa").value(1))
                .andExpect(jsonPath("$.modulosActivos[0].nombreModulo").value("Reportes"));
    }

    @Test
    void obtenerConfigTienda_deberiaRetornar404SiNoExisteConfiguracion() throws Exception {
        when(service.obtenerConfiguracionTienda(99L))
                .thenThrow(new custompage.config.exception.ResourceNotFoundException(
                        "No se encontró la configuración estética para la empresa ID: 99"));

        mockMvc.perform(get("/api/config/tienda/{idEmpresa}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No se encontró la configuración estética para la empresa ID: 99"));
    }
}