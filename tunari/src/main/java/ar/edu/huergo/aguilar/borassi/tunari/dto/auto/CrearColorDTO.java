package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;



import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;

public record CrearColorDTO(
    @NotBlank(message = "El nombre del color no puede estar vacío")
    String nombreColor,

    @NotNull(message = "Debe incluir al menos una marca")
    Long marcaId
) {}
