package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import jakarta.validation.constraints.NotBlank;

public record CrearMarcaDTO(
    @NotBlank(message = "El nombre de la marca no puede estar vacío")
    String nombreMarca

) {}
