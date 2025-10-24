package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CrearModeloDTO(
    @NotBlank(message = "El nombre del modelo no puede estar vacío")
    String nombre,

    @NotNull(message = "La marca es obligatoria") 
    Long marcaId,   

    @NotEmpty(message = "Debe incluir al menos un id de un color")
    List<Long> coloresIds,

    @NotEmpty(message = "Debe incluir al menos un id de una version")
    List<Long> versionesIds
) {}
