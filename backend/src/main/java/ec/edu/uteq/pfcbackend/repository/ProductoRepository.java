package ec.edu.uteq.pfcbackend.repository;

import ec.edu.uteq.pfcbackend.entity.Producto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Page<Producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    Optional<Producto> findBySkuIgnoreCase(String sku);
}
