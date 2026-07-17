package ec.edu.uteq.pfcbackend.repository;

import ec.edu.uteq.pfcbackend.entity.EstadoLibro;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoLibroRepository extends JpaRepository<EstadoLibro, Long> {

    Page<EstadoLibro> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    Optional<EstadoLibro> findByNombreIgnoreCase(String nombre);
}
