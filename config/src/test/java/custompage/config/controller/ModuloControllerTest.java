package custompage.config.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import custompage.config.dto.ModuloDTO;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ModuloControllerTest {

    @Mock
    private IConfigService service;

    @InjectMocks
    private ModuloController controller;

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
    void guardarModulo_deberiaRetornar200ConModuloGuardado() throws Exception {
        ModuloDTO request = ModuloDTO.builder()
                .idEmpresa(1L)
                .nombreModulo("Promociones")
                .activo(true)
                .ordenPantalla(2)
                .build();

        ModuloDTO respuesta = ModuloDTO.builder()
                .idModulo(5L)
                .idEmpresa(1L)
                .nombreModulo("Promociones")
                .activo(true)
                .ordenPantalla(2)
                .build();

        when(service.guardarOActualizarModulo(any(ModuloDTO.class))).thenReturn(respuesta);

        mockMvc.perform(post("/api/config/modulos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idModulo").value(5))
                .andExpect(jsonPath("$.nombreModulo").value("Promociones"));
    }

    @Test
    void guardarModulo_deberiaRetornar400SiFaltanCamposObligatorios() throws Exception {
        ModuloDTO requestInvalido = ModuloDTO.builder()
                .idEmpresa(1L)
                .build(); // falta nombreModulo y ordenPantalla

        mockMvc.perform(post("/api/config/modulos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }
}