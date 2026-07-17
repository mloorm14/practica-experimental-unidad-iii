package ec.edu.uteq.pfcbackend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LibroRequest(

        @NotBlank(message = "El titulo es obligatorio")
        @Size(max = 150, message = "El titulo no puede superar los 150 caracteres")
        String titulo,

        @Size(max = 500, message = "La descripcion no puede superar los 500 caracteres")
        String descripcion,

        @NotBlank(message = "El ISBN es obligatorio")
        @Size(max = 50, message = "El ISBN no puede superar los 50 caracteres")
        String isbn,

        @Size(max = 100, message = "El genero no puede superar los 100 caracteres")
        String genero,

        @NotBlank(message = "El autor es obligatorio")
        @Size(max = 150, message = "El autor no puede superar los 150 caracteres")
        String autor,

        @NotNull(message = "El anio de publicacion es obligatorio")
        @Min(value = 1000, message = "El anio de publicacion no es valido")
        @Max(value = 2100, message = "El anio de publicacion no es valido")
        Integer anioPublicacion,

        @Size(max = 150, message = "La editorial no puede superar los 150 caracteres")
        String editorial,

        @Size(max = 50, message = "El idioma no puede superar los 50 caracteres")
        String idioma,

        @Size(max = 30, message = "El estado no puede superar los 30 caracteres")
        String estado,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock
) {
}
