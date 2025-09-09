package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import java.util.List;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;


public record ModeloDTO(
    Long id,
    Marca marca,
    String nombreModelo,
    List<Version> versiones,
    List<Color> colores
) {}