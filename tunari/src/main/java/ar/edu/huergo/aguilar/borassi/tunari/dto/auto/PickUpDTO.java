package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickUpDTO extends VehiculoDTO {

    @NotNull(message = "Debe incluir el peso del baúl")
    private Integer pesoBaul;

}
