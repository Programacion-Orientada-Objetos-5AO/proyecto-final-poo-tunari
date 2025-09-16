package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;

public record AutoDTO(
    Long id,
    Marca nombreMarca,
    Modelo modelo,
    Color color,
    Version version
) {}