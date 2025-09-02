package ar.edu.huergo.aguilar.borassi.tunari.mapper;

import ar.edu.huergo.aguilar.borassi.tunari.dto.ColorDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.dto.CrearColorDTO;
import java.util.List;

public class ColorMapper {

    public static ColorDTO toDTO(Color color) {
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

    public static Color toEntity(CrearColorDTO dto, Marca marca, List<Modelo> modelos) {
        Color color = new Color();
        color.setNombreColor(dto.nombreColor());
        color.setMarca(marca);
        color.setModelos(modelos);
        return color;
    }
}