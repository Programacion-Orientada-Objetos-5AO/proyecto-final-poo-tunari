package ar.edu.huergo.aguilar.borassi.tunari.dto.agencia;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.VehiculoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Vehiculo;

public record MostrarAutoStockDTO(VehiculoDTO auto, int stock) {
    
}
