package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CrearAutoDTO(
    @NotBlank(message = "El nombre de la marca no puede estar vacío")
    String nombreMarca,

    @NotEmpty(message = "Debe incluir al menos un modelo")
    List<Long> modelosIds,

    @NotEmpty(message = "Debe incluir al menos un color")
    List<Long> coloresIds,

    @NotEmpty(message = "Debe incluir al menos una versión.")
    List<Long> versionesIds

) {}
