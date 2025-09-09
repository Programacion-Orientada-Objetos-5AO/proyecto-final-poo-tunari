package ar.edu.huergo.aguilar.borassi.tunari.mapper.auto;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.ModeloDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;

@Component
public class ModeloMapper {

    public ModeloDTO toDTO(Modelo modelo) {
        return new ModeloDTO(
            modelo.getId(),
            modelo.getMarca(),
            modelo.getNombreModelo(),
            modelo.getVersiones(),
            modelo.getColores()
        );
    }

    public Modelo toEntity(ModeloDTO dto) {
        Modelo modelo = new Modelo();
        modelo.setNombreModelo(dto.nombreModelo());
        modelo.setMarca(dto.marca());
        modelo.setVersiones(dto.versiones());
        modelo.setColores(dto.colores());
        return modelo;
    }

    public List<ModeloDTO> toDTOList(List<Modelo> modelos) {
        if (modelos == null) return List.of();
        return modelos.stream().map(this::toDTO).toList();
    }
}