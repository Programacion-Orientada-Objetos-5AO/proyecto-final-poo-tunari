package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor



public class CrearVehiculoDTO {
    
    @NotNull(message = "Debe incluir una marca")
    private Long marcaId;

    @NotNull(message = "Debe incluir un modelo")
    private Long modeloId;

    @NotNull(message = "Debe incluir un color")
    private Long colorId;

    @NotNull(message = "Debe incluir una versión")
    private Long versionId;

    @NotNull(message = "Debe especificar el tipo de vehiculo (AUTO/PICKUP)")
    private TipoVehiculo tipo;

    private Integer medicionBaul;

}
