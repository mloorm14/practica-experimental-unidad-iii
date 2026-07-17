package ec.edu.uteq.pfcbackend.controller;

import ec.edu.uteq.pfcbackend.dto.EditorialRequest;
import ec.edu.uteq.pfcbackend.dto.EditorialResponse;
import ec.edu.uteq.pfcbackend.service.EditorialService;
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
@RequestMapping("/api/editoriales")
@RequiredArgsConstructor
public class EditorialController {

    private final EditorialService editorialService;

    @GetMapping
    public Page<EditorialResponse> listar(Pageable pageable) {
        return editorialService.listar(pageable);
    }

    @GetMapping("/{id}")
    public EditorialResponse obtenerPorId(@PathVariable Long id) {
        return editorialService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EditorialResponse crear(@Valid @RequestBody EditorialRequest request) {
        return editorialService.crear(request);
    }

    @PutMapping("/{id}")
    public EditorialResponse actualizar(@PathVariable Long id, @Valid @RequestBody EditorialRequest request) {
        return editorialService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        editorialService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
