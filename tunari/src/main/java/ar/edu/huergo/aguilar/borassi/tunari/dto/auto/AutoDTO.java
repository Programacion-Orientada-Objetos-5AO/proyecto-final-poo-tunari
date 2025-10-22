package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoDTO extends VehiculoDTO {

    @NotNull(message = "Debe incluir el tamaño del baúl")
    private Integer tamanoBaul;

}
