package ec.edu.uteq.pfcbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EstadoLibroRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 30, message = "El nombre no puede superar los 30 caracteres")
        String nombre
) {
}
