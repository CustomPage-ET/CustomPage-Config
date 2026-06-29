package custompage.config.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "modulos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idModulo;

    @Column(nullable = false)
    private Long idEmpresa; // Identificador de la PYME de cosméticos

    @Column(nullable = false, length = 50)
    private String nombreModulo; // Ej: "Reportes", "Promociones", "Productos"

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false)
    private Integer ordenPantalla; // Posición de renderizado en el Frontend
}