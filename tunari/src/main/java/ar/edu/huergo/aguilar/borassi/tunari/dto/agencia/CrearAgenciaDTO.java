package ar.edu.huergo.aguilar.borassi.tunari.dto.agencia;

import java.util.List;

public record CrearAgenciaDTO(
    Long id,
    String nombre,
    String ubicacion,
    List<Long> listaAutosIds,
    Long marcaId
) {}
