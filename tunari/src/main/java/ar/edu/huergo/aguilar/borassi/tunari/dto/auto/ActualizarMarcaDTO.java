package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record ActualizarMarcaDTO(

    @NotEmpty(message = "Debe incluir al menos un modelo")
    List<Long> modelosIds,

    @NotEmpty(message = "Debe incluir al menos un color")
    List<Long> coloresIds
) {}

