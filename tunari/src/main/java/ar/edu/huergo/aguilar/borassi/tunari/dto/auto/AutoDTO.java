package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

public record AutoDTO(
    Long id,
    String nombreMarca,
    String modelo,
    String color,
    String version
) {}