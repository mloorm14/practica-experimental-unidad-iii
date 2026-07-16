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
        String editorial,
        String idioma,
        String estado,
        Integer stock,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
