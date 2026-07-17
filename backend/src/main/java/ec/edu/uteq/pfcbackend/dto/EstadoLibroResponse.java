package ec.edu.uteq.pfcbackend.dto;

import java.time.LocalDateTime;

public record EstadoLibroResponse(
        Long id,
        String nombre,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
