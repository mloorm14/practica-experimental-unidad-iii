package ec.edu.uteq.pfcbackend.service;

import ec.edu.uteq.pfcbackend.dto.EditorialRequest;
import ec.edu.uteq.pfcbackend.dto.EditorialResponse;
import ec.edu.uteq.pfcbackend.entity.Editorial;
import ec.edu.uteq.pfcbackend.exception.BusinessException;
import ec.edu.uteq.pfcbackend.exception.ResourceNotFoundException;
import ec.edu.uteq.pfcbackend.repository.EditorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EditorialServiceImpl implements EditorialService {

    private final EditorialRepository editorialRepository;

    @Override
    public Page<EditorialResponse> listar(Pageable pageable) {
        return editorialRepository.findAll(pageable).map(this::aResponse);
    }

    @Override
    public EditorialResponse obtenerPorId(Long id) {
        return aResponse(buscarOFallar(id));
    }

    @Override
    @Transactional
    public EditorialResponse crear(EditorialRequest request) {
        editorialRepository.findByNombreIgnoreCase(request.nombre()).ifPresent(e -> {
            throw new BusinessException("Ya existe una editorial con el nombre: " + request.nombre());
        });

        Editorial editorial = Editorial.builder()
                .nombre(request.nombre())
                .build();

        return aResponse(editorialRepository.save(editorial));
    }

    @Override
    @Transactional
    public EditorialResponse actualizar(Long id, EditorialRequest request) {
        Editorial editorial = buscarOFallar(id);

        editorialRepository.findByNombreIgnoreCase(request.nombre())
                .filter(otro -> !otro.getId().equals(id))
                .ifPresent(e -> {
                    throw new BusinessException("Ya existe una editorial con el nombre: " + request.nombre());
                });

        editorial.setNombre(request.nombre());

        return aResponse(editorialRepository.save(editorial));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Editorial editorial = buscarOFallar(id);
        editorialRepository.delete(editorial);
    }

    private Editorial buscarOFallar(Long id) {
        return editorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Editorial no encontrada con id: " + id));
    }

    private EditorialResponse aResponse(Editorial editorial) {
        return new EditorialResponse(
                editorial.getId(),
                editorial.getNombre(),
                editorial.getCreatedAt(),
                editorial.getUpdatedAt()
        );
    }
}
