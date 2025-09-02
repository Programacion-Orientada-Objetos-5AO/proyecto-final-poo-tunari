package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CrearColorDTO(
    @NotBlank(message = "El nombre del color no puede estar vacío")
    String nombreColor,

    @NotEmpty(message = "Debe incluir al menos un modelo")
    List<Long> modelosIds,

    @NotNull(message = "Debe incluir al menos una marca")
    Long marcaId
) {}
