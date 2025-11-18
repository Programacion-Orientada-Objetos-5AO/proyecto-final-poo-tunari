package ar.edu.huergo.aguilar.borassi.tunari.dto.calculadora;

import java.util.Map;
import java.util.function.LongConsumer;

public record EstadisticasDTO (String operacionMasUsada, Double promedioResultados, Long totalCalculos, Map<String, Long> calculosPorOperacion) {
    
}
