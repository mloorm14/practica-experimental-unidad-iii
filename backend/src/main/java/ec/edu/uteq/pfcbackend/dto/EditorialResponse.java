package ec.edu.uteq.pfcbackend.dto;

import java.time.LocalDateTime;

public record EditorialResponse(
        Long id,
        String nombre,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
