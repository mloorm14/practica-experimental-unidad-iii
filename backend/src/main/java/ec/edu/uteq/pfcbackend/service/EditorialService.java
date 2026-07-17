package ec.edu.uteq.pfcbackend.service;

import ec.edu.uteq.pfcbackend.dto.EditorialRequest;
import ec.edu.uteq.pfcbackend.dto.EditorialResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EditorialService {

    Page<EditorialResponse> listar(Pageable pageable);

    EditorialResponse obtenerPorId(Long id);

    EditorialResponse crear(EditorialRequest request);

    EditorialResponse actualizar(Long id, EditorialRequest request);

    void eliminar(Long id);
}
