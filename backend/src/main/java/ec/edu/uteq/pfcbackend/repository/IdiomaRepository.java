package ec.edu.uteq.pfcbackend.repository;

import ec.edu.uteq.pfcbackend.entity.Idioma;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdiomaRepository extends JpaRepository<Idioma, Long> {

    Page<Idioma> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    Optional<Idioma> findByNombreIgnoreCase(String nombre);
}
