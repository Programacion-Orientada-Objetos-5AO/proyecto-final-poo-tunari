package ar.edu.huergo.aguilar.borassi.tunari.mapper.auto;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.ColorDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearColorDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;

@Component
public class ColorMapper {

    public ColorDTO toDTO(Color color) {
        return new ColorDTO(
            color.getId(),
            color.getMarca().getNombre(),
            color.getNombre()
        );
    }

    public Color toEntity(CrearColorDTO colorDTO) {
        Color color = new Color();
        color.setNombre(colorDTO.nombreColor());
        return color;
    }
    public List<ColorDTO> toDTOList(List<Color> colores) {
        return colores.stream()
            .map(this::toDTO)
            .toList();
    }

}