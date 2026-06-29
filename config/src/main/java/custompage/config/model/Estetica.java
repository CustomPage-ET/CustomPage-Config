package custompage.config.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "esteticas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estetica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstetica;

    @Column(nullable = false, unique = true)
    private Long idEmpresa;

    @Column(nullable = false, length = 20)
    private String paletaColores;

    @Column(nullable = false, length = 50)
    private String fuenteTexto;

    @Column(nullable = false, length = 255)
    private String urlLogo;

    @Column(length = 255)
    private String urlBannerFondo;
}