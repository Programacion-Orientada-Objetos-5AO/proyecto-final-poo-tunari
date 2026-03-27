package ar.edu.huergo.aguilar.borassi.tunari.dto.agencia;

import java.util.List;

public record MostrarAgenciaDTO(
    Long id,
    String nombre,
    String ubicacion,
    List<MostrarAutoStockDTO> listaAutos,
    String marca
) {}
