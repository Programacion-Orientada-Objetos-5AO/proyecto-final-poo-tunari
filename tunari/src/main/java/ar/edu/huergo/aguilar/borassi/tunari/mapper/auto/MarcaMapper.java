package ar.edu.huergo.aguilar.borassi.tunari.mapper.auto;

import java.util.List;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearMarcaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.MarcaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;


public class MarcaMapper {

    public static MarcaDTO toDTO(Marca marca) {
        List<String> modelos = marca.getModelos().stream()
            .map(Modelo::getNombreModelo)
            .toList();

        List<String> colores = marca.getColores().stream()
            .map(Color::getNombreColor)
            .toList();

        return new MarcaDTO(
            marca.getId(),
            marca.getNombreMarca(),
            modelos,
            colores
        );
    }

    public static Marca toEntity(CrearMarcaDTO dto, List<Modelo> modelos, List<Color> colores) {
        Marca marca = new Marca();
        marca.setNombreMarca(dto.nombreMarca());
        marca.setModelos(modelos);
        marca.setColores(colores);
        return marca;
    }
}
