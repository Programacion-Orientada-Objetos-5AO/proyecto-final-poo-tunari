package ar.edu.huergo.aguilar.borassi.tunari.dto.agencia;

import java.util.List;

public record AgregarAutosAgenciaDTO (
    Long idAgencia,
    List<Long> listaAutoStockIds
) {}
