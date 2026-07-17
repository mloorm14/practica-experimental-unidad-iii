package ec.edu.uteq.pfcbackend.dto;

import java.time.LocalDateTime;

public record LibroResponse(
        Long id,
        String titulo,
        String descripcion,
        String isbn,
        String genero,
        String autor,
        Integer anioPublicacion,
        Long editorialId,
        String editorialNombre,
        Long idiomaId,
        String idiomaNombre,
        Long estadoId,
        String estadoNombre,
        Integer stock,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
