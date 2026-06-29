package custompage.config.controller;

import custompage.config.dto.EsteticaDTO;
import custompage.config.dto.TiendaConfigCompletaDTO;
import custompage.config.service.IConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class EsteticaController {

    private final IConfigService service;
    public EsteticaController(IConfigService service) { this.service = service; }

    @PostMapping("/estetica")
    public ResponseEntity<EsteticaDTO> guardarEstetica(@Validated @RequestBody EsteticaDTO dto) {
        return ResponseEntity.ok(service.guardarOActualizarEstetica(dto));
    }

    // Endpoint maestro que invoca el BFF al cargar la aplicación web de la tienda
    @GetMapping("/tienda/{idEmpresa}")
    public ResponseEntity<TiendaConfigCompletaDTO> obtenerConfigTienda(@PathVariable Long idEmpresa) {
        return ResponseEntity.ok(service.obtenerConfiguracionTienda(idEmpresa));
    }
}