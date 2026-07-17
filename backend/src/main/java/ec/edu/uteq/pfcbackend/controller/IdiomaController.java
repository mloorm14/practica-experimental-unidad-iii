package ec.edu.uteq.pfcbackend.controller;

import ec.edu.uteq.pfcbackend.dto.IdiomaRequest;
import ec.edu.uteq.pfcbackend.dto.IdiomaResponse;
import ec.edu.uteq.pfcbackend.service.IdiomaService;
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
@RequestMapping("/api/idiomas")
@RequiredArgsConstructor
public class IdiomaController {

    private final IdiomaService idiomaService;

    @GetMapping
    public Page<IdiomaResponse> listar(Pageable pageable) {
        return idiomaService.listar(pageable);
    }

    @GetMapping("/{id}")
    public IdiomaResponse obtenerPorId(@PathVariable Long id) {
        return idiomaService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdiomaResponse crear(@Valid @RequestBody IdiomaRequest request) {
        return idiomaService.crear(request);
    }

    @PutMapping("/{id}")
    public IdiomaResponse actualizar(@PathVariable Long id, @Valid @RequestBody IdiomaRequest request) {
        return idiomaService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        idiomaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
