package ar.edu.huergo.aguilar.borassi.tunari.dto.agencia;

public record CrearAgenciaDTO(
    Long id,
    String nombre,
    String ubicacion,
    Long marcaId
) {}
