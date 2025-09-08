package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import java.util.List;

public record AutoDTO(
    Long id,
    String nombreMarca,
    List<String> modelos,
    List<String> colores,
    List<String> versiones
) {}