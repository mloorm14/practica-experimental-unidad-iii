package ec.edu.uteq.pfcbackend.service;

import ec.edu.uteq.pfcbackend.dto.EstadoLibroRequest;
import ec.edu.uteq.pfcbackend.dto.EstadoLibroResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstadoLibroService {

    Page<EstadoLibroResponse> listar(Pageable pageable);

    EstadoLibroResponse obtenerPorId(Long id);

    EstadoLibroResponse crear(EstadoLibroRequest request);

    EstadoLibroResponse actualizar(Long id, EstadoLibroRequest request);

    void eliminar(Long id);
}
