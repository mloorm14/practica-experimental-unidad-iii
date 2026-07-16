package ec.edu.uteq.pfcbackend.service;

import ec.edu.uteq.pfcbackend.config.CacheablePage;
import ec.edu.uteq.pfcbackend.dto.LibroRequest;
import ec.edu.uteq.pfcbackend.dto.LibroResponse;
import ec.edu.uteq.pfcbackend.entity.Libro;
import ec.edu.uteq.pfcbackend.exception.BusinessException;
import ec.edu.uteq.pfcbackend.exception.ResourceNotFoundException;
import ec.edu.uteq.pfcbackend.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibroServiceImpl implements LibroService {

    private static final String CACHE_LISTADO = "libros_listado";

    private final LibroRepository libroRepository;

    @Override
    @Cacheable(value = CACHE_LISTADO, key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort.toString()")
    public Page<LibroResponse> listar(Pageable pageable) {
        Page<Libro> pagina = libroRepository.findAll(pageable);
        return new CacheablePage<>(
                pagina.getContent().stream().map(this::aResponse).toList(),
                pagina.getNumber(),
                pagina.getSize(),
                pagina.getTotalElements()
        );
    }

    @Override
    public LibroResponse obtenerPorId(Long id) {
        return aResponse(buscarOFallar(id));
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_LISTADO, allEntries = true)
    public LibroResponse crear(LibroRequest request) {
        libroRepository.findByIsbnIgnoreCase(request.isbn()).ifPresent(l -> {
            throw new BusinessException("Ya existe un libro con el ISBN: " + request.isbn());
        });

        Libro libro = Libro.builder()
                .titulo(request.titulo())
                .descripcion(request.descripcion())
                .isbn(request.isbn())
                .genero(request.genero())
                .autor(request.autor())
                .anioPublicacion(request.anioPublicacion())
                .editorial(request.editorial())
                .idioma(request.idioma())
                .estado(request.estado())
                .stock(request.stock())
                .build();

        return aResponse(libroRepository.save(libro));
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_LISTADO, allEntries = true)
    public LibroResponse actualizar(Long id, LibroRequest request) {
        Libro libro = buscarOFallar(id);

        libroRepository.findByIsbnIgnoreCase(request.isbn())
                .filter(otro -> !otro.getId().equals(id))
                .ifPresent(l -> {
                    throw new BusinessException("Ya existe un libro con el ISBN: " + request.isbn());
                });

        libro.setTitulo(request.titulo());
        libro.setDescripcion(request.descripcion());
        libro.setIsbn(request.isbn());
        libro.setGenero(request.genero());
        libro.setAutor(request.autor());
        libro.setAnioPublicacion(request.anioPublicacion());
        libro.setEditorial(request.editorial());
        libro.setIdioma(request.idioma());
        libro.setEstado(request.estado());
        libro.setStock(request.stock());

        return aResponse(libroRepository.save(libro));
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_LISTADO, allEntries = true)
    public void eliminar(Long id) {
        Libro libro = buscarOFallar(id);
        libroRepository.delete(libro);
    }

    private Libro buscarOFallar(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));
    }

    private LibroResponse aResponse(Libro libro) {
        return new LibroResponse(
                libro.getId(),
                libro.getTitulo(),
                libro.getDescripcion(),
                libro.getIsbn(),
                libro.getGenero(),
                libro.getAutor(),
                libro.getAnioPublicacion(),
                libro.getEditorial(),
                libro.getIdioma(),
                libro.getEstado(),
                libro.getStock(),
                libro.getCreatedAt(),
                libro.getUpdatedAt()
        );
    }
}
