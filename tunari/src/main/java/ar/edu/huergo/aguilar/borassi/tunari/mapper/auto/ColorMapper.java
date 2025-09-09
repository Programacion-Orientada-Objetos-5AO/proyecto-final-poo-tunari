package ar.edu.huergo.aguilar.borassi.tunari.mapper.auto;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.ColorDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;

@Component
public class ColorMapper {

    public ColorDTO toDTO(Color color) {
        List<String> modelos = color.getModelos().stream()
            .map(Modelo::getNombreModelo)
            .toList();

        return new ColorDTO(
            color.getId(),
            color.getMarca().getNombreMarca(),
            color.getNombreColor(),
            modelos
        );
    }


    public List<ColorDTO> toDTOList(List<Color> colores) {
        return colores.stream()
            .map(this::toDTO)
            .toList();
    }

}