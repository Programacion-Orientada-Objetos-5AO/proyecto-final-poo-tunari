package ar.edu.huergo.aguilar.borassi.tunari.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CrearColorDTO(
    @NotBlank(message = "El nombre del color no puede estar vacío")
    String nombreColor,

    @NotEmpty(message = "Debe incluir al menos un modelo")
    List<Long> modelosIds,

    @NotNull(message = "Debe incluir al menos una marca")
    Long marcaId
) {}
