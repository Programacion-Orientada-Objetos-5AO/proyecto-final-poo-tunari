package ar.edu.huergo.aguilar.borassi.tunari.dto.auto;

import java.util.List;

public record ModeloDTO(
    Long id,
    String nombreModelo,
    Long marcaId,
    String nombreMarca,
    List<String> versiones, 
    List<String> colores    
) {}