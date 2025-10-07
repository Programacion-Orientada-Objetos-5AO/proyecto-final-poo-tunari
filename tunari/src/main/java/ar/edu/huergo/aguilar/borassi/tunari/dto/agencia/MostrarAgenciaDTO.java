package ar.edu.huergo.aguilar.borassi.tunari.dto.agencia;

import java.util.List;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;

public record MostrarAgenciaDTO(
    Long id,
    String nombre,
    String ubicacion,
    List<MostrarAutoStockDTO> listaAutosIds,
    Marca marcaId
) {}
