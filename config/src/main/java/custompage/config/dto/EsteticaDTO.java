package custompage.config.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsteticaDTO {
    private Long idEstetica;

    @NotNull(message = "El idEmpresa es obligatorio")
    private Long idEmpresa;

    @NotBlank(message = "La paleta de colores es obligatoria")
    private String paletaColores;

    @NotBlank(message = "La fuente de texto es obligatoria")
    private String fuenteTexto;

    @NotBlank(message = "La URL del logo es obligatoria")
    private String urlLogo;

    private String urlBannerFondo;
}