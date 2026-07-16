package ec.edu.uteq.pfcbackend.service;

import ec.edu.uteq.pfcbackend.dto.ProductoRequest;
import ec.edu.uteq.pfcbackend.dto.ProductoResponse;
import ec.edu.uteq.pfcbackend.entity.Producto;
import ec.edu.uteq.pfcbackend.exception.BusinessException;
import ec.edu.uteq.pfcbackend.exception.ResourceNotFoundException;
import ec.edu.uteq.pfcbackend.config.CacheablePage;
import ec.edu.uteq.pfcbackend.repository.ProductoRepository;
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
public class ProductoServiceImpl implements ProductoService {

    private static final String CACHE_LISTADO = "productos_listado";

    private final ProductoRepository productoRepository;

    @Override
    @Cacheable(value = CACHE_LISTADO, key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort.toString()")
    public Page<ProductoResponse> listar(Pageable pageable) {
        Page<Producto> pagina = productoRepository.findAll(pageable);
        return new CacheablePage<>(
                pagina.getContent().stream().map(this::aResponse).toList(),
                pagina.getNumber(),
                pagina.getSize(),
                pagina.getTotalElements()
        );
    }

    @Override
    public ProductoResponse obtenerPorId(Long id) {
        return aResponse(buscarOFallar(id));
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_LISTADO, allEntries = true)
    public ProductoResponse crear(ProductoRequest request) {
        productoRepository.findBySkuIgnoreCase(request.sku()).ifPresent(p -> {
            throw new BusinessException("Ya existe un producto con el SKU: " + request.sku());
        });

        Producto producto = Producto.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .sku(request.sku())
                .precio(request.precio())
                .stock(request.stock())
                .categoria(request.categoria())
                .build();

        return aResponse(productoRepository.save(producto));
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_LISTADO, allEntries = true)
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = buscarOFallar(id);

        productoRepository.findBySkuIgnoreCase(request.sku())
                .filter(otro -> !otro.getId().equals(id))
                .ifPresent(p -> {
                    throw new BusinessException("Ya existe un producto con el SKU: " + request.sku());
                });

        producto.setNombre(request.nombre());
        producto.setDescripcion(request.descripcion());
        producto.setSku(request.sku());
        producto.setPrecio(request.precio());
        producto.setStock(request.stock());
        producto.setCategoria(request.categoria());

        return aResponse(productoRepository.save(producto));
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_LISTADO, allEntries = true)
    public void eliminar(Long id) {
        Producto producto = buscarOFallar(id);
        productoRepository.delete(producto);
    }

    private Producto buscarOFallar(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }

    private ProductoResponse aResponse(Producto producto) {
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getSku(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria(),
                producto.getCreatedAt(),
                producto.getUpdatedAt()
        );
    }
}
