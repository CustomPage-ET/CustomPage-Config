package custompage.config.repository;

import custompage.config.model.Estetica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EsteticaRepository extends JpaRepository<Estetica, Long> {
    Optional<Estetica> findByIdEmpresa(Long idEmpresa);
}