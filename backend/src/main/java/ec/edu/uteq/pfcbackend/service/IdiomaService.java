package ec.edu.uteq.pfcbackend.service;

import ec.edu.uteq.pfcbackend.dto.IdiomaRequest;
import ec.edu.uteq.pfcbackend.dto.IdiomaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IdiomaService {

    Page<IdiomaResponse> listar(Pageable pageable);

    IdiomaResponse obtenerPorId(Long id);

    IdiomaResponse crear(IdiomaRequest request);

    IdiomaResponse actualizar(Long id, IdiomaRequest request);

    void eliminar(Long id);
}
