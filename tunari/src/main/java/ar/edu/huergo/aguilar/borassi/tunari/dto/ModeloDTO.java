package ar.edu.huergo.aguilar.borassi.tunari.dto;

import java.util.List;

public record ModeloDTO(
    Long id,
    String nombreMarca,
    String nombreModelo,
    List<String> versiones,
    List<String> colores
) {}