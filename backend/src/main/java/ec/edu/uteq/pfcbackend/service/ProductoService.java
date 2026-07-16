package ec.edu.uteq.pfcbackend.service;

import ec.edu.uteq.pfcbackend.dto.ProductoRequest;
import ec.edu.uteq.pfcbackend.dto.ProductoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoService {

    Page<ProductoResponse> listar(Pageable pageable);

    ProductoResponse obtenerPorId(Long id);

    ProductoResponse crear(ProductoRequest request);

    ProductoResponse actualizar(Long id, ProductoRequest request);

    void eliminar(Long id);
}
