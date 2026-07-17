package ec.edu.uteq.pfcbackend.repository;

import ec.edu.uteq.pfcbackend.entity.Editorial;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditorialRepository extends JpaRepository<Editorial, Long> {

    Page<Editorial> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    Optional<Editorial> findByNombreIgnoreCase(String nombre);
}
