package ec.edu.uteq.pfcbackend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductoResponse(
        Long id,
        String nombre,
        String descripcion,
        String sku,
        BigDecimal precio,
        Integer stock,
        String categoria,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
