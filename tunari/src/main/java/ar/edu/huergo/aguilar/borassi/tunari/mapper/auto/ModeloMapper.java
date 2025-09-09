package ar.edu.huergo.aguilar.borassi.tunari.mapper.auto;

import java.util.List;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearModeloDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.ModeloDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;


public class ModeloMapper {

    public ModeloDTO toDTO(Modelo modelo) {
        return new ModeloDTO(
            modelo.getId(),
            modelo.getMarca().getNombreMarca(),
            modelo.getNombreModelo(),
            modelo.getVersiones(),
            modelo.getColores()
        );
    }

    public Modelo toEntity(CrearModeloDTO dto, Marca marca, List<Version> versiones, List<Color> colores) {
        Modelo modelo = new Modelo();
        modelo.setNombreModelo(dto.nombreModelo());
        modelo.setMarca(marca);
        modelo.setVersiones(versiones);
        modelo.setColores(colores);
        return modelo;
    }

    public List<ModeloDTO> toDTOList(List<Modelo> modelos) {
        if (modelos == null) return List.of();
        return modelos.stream().map(this::toDTO).toList();
    }
}