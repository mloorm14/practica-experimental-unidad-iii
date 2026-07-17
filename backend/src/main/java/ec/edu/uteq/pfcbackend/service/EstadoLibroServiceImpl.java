package ec.edu.uteq.pfcbackend.service;

import ec.edu.uteq.pfcbackend.dto.EstadoLibroRequest;
import ec.edu.uteq.pfcbackend.dto.EstadoLibroResponse;
import ec.edu.uteq.pfcbackend.entity.EstadoLibro;
import ec.edu.uteq.pfcbackend.exception.BusinessException;
import ec.edu.uteq.pfcbackend.exception.ResourceNotFoundException;
import ec.edu.uteq.pfcbackend.repository.EstadoLibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstadoLibroServiceImpl implements EstadoLibroService {

    private final EstadoLibroRepository estadoLibroRepository;

    @Override
    public Page<EstadoLibroResponse> listar(Pageable pageable) {
        return estadoLibroRepository.findAll(pageable).map(this::aResponse);
    }

    @Override
    public EstadoLibroResponse obtenerPorId(Long id) {
        return aResponse(buscarOFallar(id));
    }

    @Override
    @Transactional
    public EstadoLibroResponse crear(EstadoLibroRequest request) {
        estadoLibroRepository.findByNombreIgnoreCase(request.nombre()).ifPresent(e -> {
            throw new BusinessException("Ya existe un estado con el nombre: " + request.nombre());
        });

        EstadoLibro estado = EstadoLibro.builder()
                .nombre(request.nombre())
                .build();

        return aResponse(estadoLibroRepository.save(estado));
    }

    @Override
    @Transactional
    public EstadoLibroResponse actualizar(Long id, EstadoLibroRequest request) {
        EstadoLibro estado = buscarOFallar(id);

        estadoLibroRepository.findByNombreIgnoreCase(request.nombre())
                .filter(otro -> !otro.getId().equals(id))
                .ifPresent(e -> {
                    throw new BusinessException("Ya existe un estado con el nombre: " + request.nombre());
                });

        estado.setNombre(request.nombre());

        return aResponse(estadoLibroRepository.save(estado));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        EstadoLibro estado = buscarOFallar(id);
        estadoLibroRepository.delete(estado);
    }

    private EstadoLibro buscarOFallar(Long id) {
        return estadoLibroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado de libro no encontrado con id: " + id));
    }

    private EstadoLibroResponse aResponse(EstadoLibro estado) {
        return new EstadoLibroResponse(
                estado.getId(),
                estado.getNombre(),
                estado.getCreatedAt(),
                estado.getUpdatedAt()
        );
    }
}
