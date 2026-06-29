package custompage.config.service;

import custompage.config.dto.EsteticaDTO;
import custompage.config.dto.ModuloDTO;
import custompage.config.dto.TiendaConfigCompletaDTO;
import custompage.config.exception.ResourceNotFoundException;
import custompage.config.model.Estetica;
import custompage.config.model.Modulo;
import custompage.config.repository.EsteticaRepository;
import custompage.config.repository.ModuloRepository;
import custompage.config.service.IConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfigServiceImpl implements IConfigService {

    private final EsteticaRepository esteticaRepo;
    private final ModuloRepository moduloRepo;

    public ConfigServiceImpl(EsteticaRepository esteticaRepo, ModuloRepository moduloRepo) {
        this.esteticaRepo = esteticaRepo;
        this.moduloRepo = moduloRepo;
    }

    @Override
    @Transactional
    public EsteticaDTO guardarOActualizarEstetica(EsteticaDTO dto) {
        Estetica estetica = esteticaRepo.findByIdEmpresa(dto.getIdEmpresa())
                .orElse(new Estetica());

        estetica.setIdEmpresa(dto.getIdEmpresa());
        estetica.setPaletaColores(dto.getPaletaColores());
        estetica.setFuenteTexto(dto.getFuenteTexto());
        estetica.setUrlLogo(dto.getUrlLogo());
        estetica.setUrlBannerFondo(dto.getUrlBannerFondo());

        Estetica guardada = esteticaRepo.save(estetica);
        dto.setIdEstetica(guardada.getIdEstetica());
        return dto;
    }

    @Override
    @Transactional
    public ModuloDTO guardarOActualizarModulo(ModuloDTO dto) {
        Modulo modulo = Modulo.builder()
                .idModulo(dto.getIdModulo())
                .idEmpresa(dto.getIdEmpresa())
                .nombreModulo(dto.getNombreModulo())
                .activo(dto.getActivo() != null ? dto.getActivo() : true)
                .ordenPantalla(dto.getOrdenPantalla())
                .build();

        modulo = moduloRepo.save(modulo);
        dto.setIdModulo(modulo.getIdModulo());
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public TiendaConfigCompletaDTO obtenerConfiguracionTienda(Long idEmpresa) {
        Estetica estetica = esteticaRepo.findByIdEmpresa(idEmpresa)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la configuración estética para la empresa ID: " + idEmpresa));

        List<ModuloDTO> modulos = moduloRepo.findByIdEmpresaOrderByOrdenPantallaAsc(idEmpresa)
                .stream()
                .map(m -> ModuloDTO.builder()
                        .idModulo(m.getIdModulo())
                        .idEmpresa(m.getIdEmpresa())
                        .nombreModulo(m.getNombreModulo())
                        .activo(m.getActivo())
                        .ordenPantalla(m.getOrdenPantalla())
                        .build())
                .collect(Collectors.toList());

        EsteticaDTO esteticaDTO = EsteticaDTO.builder()
                .idEstetica(estetica.getIdEstetica())
                .idEmpresa(estetica.getIdEmpresa())
                .paletaColores(estetica.getPaletaColores())
                .fuenteTexto(estetica.getFuenteTexto())
                .urlLogo(estetica.getUrlLogo())
                .urlBannerFondo(estetica.getUrlBannerFondo())
                .build();

        return TiendaConfigCompletaDTO.builder()
                .idEmpresa(idEmpresa)
                .estetica(esteticaDTO)
                .modulosActivos(modulos)
                .build();
    }
}