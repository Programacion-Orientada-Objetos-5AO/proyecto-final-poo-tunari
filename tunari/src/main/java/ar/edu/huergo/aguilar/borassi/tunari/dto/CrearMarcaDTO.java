package ar.edu.huergo.aguilar.borassi.tunari.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CrearMarcaDTO(
    @NotBlank(message = "El nombre de la marca no puede estar vacío")
    String nombreMarca,

    @NotEmpty(message = "Debe incluir al menos un modelo")
    List<Long> modelosIds,

    @NotEmpty(message = "Debe incluir al menos un color")
    List<Long> coloresIds
) {}
