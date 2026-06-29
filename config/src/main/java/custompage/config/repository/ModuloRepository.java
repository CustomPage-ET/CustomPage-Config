package custompage.config.repository;

import custompage.config.model.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {
    List<Modulo> findByIdEmpresaOrderByOrdenPantallaAsc(Long idEmpresa);
}