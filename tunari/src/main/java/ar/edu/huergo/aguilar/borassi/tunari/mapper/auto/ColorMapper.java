package ar.edu.huergo.aguilar.borassi.tunari.mapper.auto;

import java.util.List;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.ColorDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearColorDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;


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