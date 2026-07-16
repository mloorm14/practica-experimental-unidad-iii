package ec.edu.uteq.pfcbackend.repository;

import ec.edu.uteq.pfcbackend.entity.Libro;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Page<Libro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    Optional<Libro> findByIsbnIgnoreCase(String isbn);
}
