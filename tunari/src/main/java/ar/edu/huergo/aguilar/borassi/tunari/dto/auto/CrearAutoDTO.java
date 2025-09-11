package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import jakarta.validation.constraints.NotNull;


public record CrearAutoDTO(
    @NotNull(message = "Debe incluir una marca")
    Long marcaId,

    @NotNull(message = "Debe incluir un modelo")
    Long modeloId,

    @NotNull(message = "Debe incluir un color")
    Long colorId,

    @NotNull(message = "Debe incluir una versión")
    Long versionId

) {}
