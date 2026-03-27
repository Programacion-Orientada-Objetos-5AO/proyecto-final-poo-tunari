package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {

    private Long id;
    private String nombreMarca;
    private String modelo;
    private String color;
    private String version;

}
