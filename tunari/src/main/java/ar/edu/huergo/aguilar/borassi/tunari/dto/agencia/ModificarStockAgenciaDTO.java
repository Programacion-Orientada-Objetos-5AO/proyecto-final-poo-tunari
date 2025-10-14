package ar.edu.huergo.aguilar.borassi.tunari.dto.agencia;


public record ModificarStockAgenciaDTO (
    Long idAgencia,
    Long idAuto,
    int nuevoStock
) {}
