package custompage.config.service;

import custompage.config.dto.EsteticaDTO;
import custompage.config.dto.ModuloDTO;
import custompage.config.dto.TiendaConfigCompletaDTO;
import custompage.config.exception.ResourceNotFoundException;
import custompage.config.model.Estetica;
import custompage.config.model.Modulo;
import custompage.config.repository.EsteticaRepository;
import custompage.config.repository.ModuloRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigServiceImplTest {

    @Mock
    private EsteticaRepository esteticaRepo;

    @Mock
    private ModuloRepository moduloRepo;

    @InjectMocks
    private ConfigServiceImpl service;

    @Test
    void guardarOActualizarEstetica_deberiaActualizarSiYaExiste() {
        EsteticaDTO dto = EsteticaDTO.builder()
                .idEmpresa(1L).paletaColores("#123456")
                .fuenteTexto("Roboto").urlLogo("http://logo.com").build();

        Estetica existente = Estetica.builder().idEstetica(7L).idEmpresa(1L).build();

        when(esteticaRepo.findByIdEmpresa(1L)).thenReturn(Optional.of(existente));
        when(esteticaRepo.save(any(Estetica.class))).thenAnswer(inv -> inv.getArgument(0));

        EsteticaDTO resultado = service.guardarOActualizarEstetica(dto);

        assertThat(resultado.getIdEstetica()).isEqualTo(7L);
        assertThat(resultado.getPaletaColores()).isEqualTo("#123456");
        verify(esteticaRepo).save(any(Estetica.class));
    }

    @Test
    void guardarOActualizarEstetica_deberiaCrearNuevaSiNoExiste() {
        EsteticaDTO dto = EsteticaDTO.builder()
                .idEmpresa(2L).paletaColores("#000000")
                .fuenteTexto("Arial").urlLogo("http://logo2.com").build();

        when(esteticaRepo.findByIdEmpresa(2L)).thenReturn(Optional.empty());
        when(esteticaRepo.save(any(Estetica.class))).thenAnswer(inv -> {
            Estetica e = inv.getArgument(0);
            e.setIdEstetica(15L);
            return e;
        });

        EsteticaDTO resultado = service.guardarOActualizarEstetica(dto);

        assertThat(resultado.getIdEstetica()).isEqualTo(15L);
    }

    @Test
    void guardarOActualizarModulo_deberiaUsarActivoTrueSiEsNulo() {
        ModuloDTO dto = ModuloDTO.builder()
                .idEmpresa(1L).nombreModulo("Reportes")
                .activo(null).ordenPantalla(1).build();

        when(moduloRepo.save(any(Modulo.class))).thenAnswer(inv -> inv.getArgument(0));

        service.guardarOActualizarModulo(dto);

        verify(moduloRepo).save(argThat(m -> m.getActivo().equals(true)));
    }

    @Test
    void guardarOActualizarModulo_deberiaRespetarActivoExplicito() {
        ModuloDTO dto = ModuloDTO.builder()
                .idEmpresa(1L).nombreModulo("Promociones")
                .activo(false).ordenPantalla(2).build();

        when(moduloRepo.save(any(Modulo.class))).thenAnswer(inv -> inv.getArgument(0));

        service.guardarOActualizarModulo(dto);

        verify(moduloRepo).save(argThat(m -> m.getActivo().equals(false)));
    }

    @Test
    void obtenerConfiguracionTienda_deberiaRetornarConfiguracionCompleta() {
        Estetica estetica = Estetica.builder()
                .idEstetica(1L).idEmpresa(1L).paletaColores("#FFF")
                .fuenteTexto("Roboto").urlLogo("http://logo.com").build();

        Modulo modulo = Modulo.builder()
                .idModulo(1L).idEmpresa(1L).nombreModulo("Reportes")
                .activo(true).ordenPantalla(1).build();

        when(esteticaRepo.findByIdEmpresa(1L)).thenReturn(Optional.of(estetica));
        when(moduloRepo.findByIdEmpresaOrderByOrdenPantallaAsc(1L)).thenReturn(List.of(modulo));

        TiendaConfigCompletaDTO resultado = service.obtenerConfiguracionTienda(1L);

        assertThat(resultado.getIdEmpresa()).isEqualTo(1L);
        assertThat(resultado.getEstetica().getPaletaColores()).isEqualTo("#FFF");
        assertThat(resultado.getModulosActivos()).hasSize(1);
        assertThat(resultado.getModulosActivos().get(0).getNombreModulo()).isEqualTo("Reportes");
    }

    @Test
    void obtenerConfiguracionTienda_deberiaLanzarExcepcionSiNoExisteEstetica() {
        when(esteticaRepo.findByIdEmpresa(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.obtenerConfiguracionTienda(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}