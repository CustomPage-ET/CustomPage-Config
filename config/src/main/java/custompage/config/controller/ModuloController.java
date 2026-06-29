package custompage.config.controller;

import custompage.config.dto.ModuloDTO;
import custompage.config.service.IConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config/modulos")
public class ModuloController {

    private final IConfigService service;
    public ModuloController(IConfigService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ModuloDTO> guardarModulo(@Validated @RequestBody ModuloDTO dto) {
        return ResponseEntity.ok(service.guardarOActualizarModulo(dto));
    }
}