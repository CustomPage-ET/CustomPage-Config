package custompage.config.service;

import custompage.config.dto.EsteticaDTO;
import custompage.config.dto.ModuloDTO;
import custompage.config.dto.TiendaConfigCompletaDTO;

public interface IConfigService {
    EsteticaDTO guardarOActualizarEstetica(EsteticaDTO dto);
    ModuloDTO guardarOActualizarModulo(ModuloDTO dto);
    TiendaConfigCompletaDTO obtenerConfiguracionTienda(Long idEmpresa);
}