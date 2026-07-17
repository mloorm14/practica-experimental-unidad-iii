package ec.edu.uteq.pfcbackend.service;

import ec.edu.uteq.pfcbackend.dto.IdiomaRequest;
import ec.edu.uteq.pfcbackend.dto.IdiomaResponse;
import ec.edu.uteq.pfcbackend.entity.Idioma;
import ec.edu.uteq.pfcbackend.exception.BusinessException;
import ec.edu.uteq.pfcbackend.exception.ResourceNotFoundException;
import ec.edu.uteq.pfcbackend.repository.IdiomaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IdiomaServiceImpl implements IdiomaService {

    private final IdiomaRepository idiomaRepository;

    @Override
    public Page<IdiomaResponse> listar(Pageable pageable) {
        return idiomaRepository.findAll(pageable).map(this::aResponse);
    }

    @Override
    public IdiomaResponse obtenerPorId(Long id) {
        return aResponse(buscarOFallar(id));
    }

    @Override
    @Transactional
    public IdiomaResponse crear(IdiomaRequest request) {
        idiomaRepository.findByNombreIgnoreCase(request.nombre()).ifPresent(i -> {
            throw new BusinessException("Ya existe un idioma con el nombre: " + request.nombre());
        });

        Idioma idioma = Idioma.builder()
                .nombre(request.nombre())
                .build();

        return aResponse(idiomaRepository.save(idioma));
    }

    @Override
    @Transactional
    public IdiomaResponse actualizar(Long id, IdiomaRequest request) {
        Idioma idioma = buscarOFallar(id);

        idiomaRepository.findByNombreIgnoreCase(request.nombre())
                .filter(otro -> !otro.getId().equals(id))
                .ifPresent(i -> {
                    throw new BusinessException("Ya existe un idioma con el nombre: " + request.nombre());
                });

        idioma.setNombre(request.nombre());

        return aResponse(idiomaRepository.save(idioma));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Idioma idioma = buscarOFallar(id);
        idiomaRepository.delete(idioma);
    }

    private Idioma buscarOFallar(Long id) {
        return idiomaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Idioma no encontrado con id: " + id));
    }

    private IdiomaResponse aResponse(Idioma idioma) {
        return new IdiomaResponse(
                idioma.getId(),
                idioma.getNombre(),
                idioma.getCreatedAt(),
                idioma.getUpdatedAt()
        );
    }
}
