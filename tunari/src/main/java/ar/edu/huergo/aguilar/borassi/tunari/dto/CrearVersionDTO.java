package ar.edu.huergo.aguilar.borassi.tunari.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearVersionDTO(
    @NotBlank(message = "El nombre de la versión no puede estar vacío")
    String nombreVersion,

    @NotNull(message = "Debe incluir la marca")
    Long marcaId
) {}
