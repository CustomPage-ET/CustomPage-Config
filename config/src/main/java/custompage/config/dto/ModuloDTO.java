package custompage.config.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuloDTO {
    private Long idModulo;

    @NotNull(message = "El idEmpresa es obligatorio")
    private Long idEmpresa;

    @NotBlank(message = "El nombre del módulo no puede estar vacío")
    private String nombreModulo;

    private Boolean activo;

    @NotNull(message = "El orden en pantalla es obligatorio")
    private Integer ordenPantalla;
}