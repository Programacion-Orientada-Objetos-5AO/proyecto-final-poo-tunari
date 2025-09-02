package ar.edu.huergo.aguilar.borassi.tunari.dto;

import java.util.List;

public record ColorDTO(
    Long id,
    String nombreMarca,
    String nombreColor,
    List<String> modelos    
) {}