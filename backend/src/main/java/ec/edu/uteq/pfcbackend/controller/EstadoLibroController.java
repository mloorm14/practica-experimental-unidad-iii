package ec.edu.uteq.pfcbackend.controller;

import ec.edu.uteq.pfcbackend.dto.EstadoLibroRequest;
import ec.edu.uteq.pfcbackend.dto.EstadoLibroResponse;
import ec.edu.uteq.pfcbackend.service.EstadoLibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estados-libro")
@RequiredArgsConstructor
public class EstadoLibroController {

    private final EstadoLibroService estadoLibroService;

    @GetMapping
    public Page<EstadoLibroResponse> listar(Pageable pageable) {
        return estadoLibroService.listar(pageable);
    }

    @GetMapping("/{id}")
    public EstadoLibroResponse obtenerPorId(@PathVariable Long id) {
        return estadoLibroService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoLibroResponse crear(@Valid @RequestBody EstadoLibroRequest request) {
        return estadoLibroService.crear(request);
    }

    @PutMapping("/{id}")
    public EstadoLibroResponse actualizar(@PathVariable Long id, @Valid @RequestBody EstadoLibroRequest request) {
        return estadoLibroService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estadoLibroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
