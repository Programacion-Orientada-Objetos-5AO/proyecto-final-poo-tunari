package ar.edu.huergo.aguilar.borassi.tunari.mapper.auto;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearModeloDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.ModeloDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;

@Component
public class ModeloMapper {

    public static ModeloDTO toDTO(Modelo modelo) {
        List<String> versiones = modelo.getVersiones().stream()
            .map(Version::getNombreVersion)
            .toList();

        List<String> colores = modelo.getColores().stream()
            .map(Color::getNombreColor)
            .toList();

        return new ModeloDTO(
            modelo.getId(),
            modelo.getNombreModelo(),
            modelo.getMarca().getNombreMarca(),
            versiones,
            colores
        );
    }

    public static Modelo toEntity(CrearModeloDTO dto, Marca marca, List<Version> versiones, List<Color> colores) {
        Modelo modelo = new Modelo();
        modelo.setNombreModelo(dto.nombreModelo());
        modelo.setMarca(marca);
        modelo.setVersiones(versiones);
        modelo.setColores(colores);
        return modelo;
    }
}