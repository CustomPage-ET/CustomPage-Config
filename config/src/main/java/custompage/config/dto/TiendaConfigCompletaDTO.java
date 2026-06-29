package custompage.config.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TiendaConfigCompletaDTO {
    private Long idEmpresa;
    private EsteticaDTO estetica;
    private List<ModuloDTO> modulosActivos;
}