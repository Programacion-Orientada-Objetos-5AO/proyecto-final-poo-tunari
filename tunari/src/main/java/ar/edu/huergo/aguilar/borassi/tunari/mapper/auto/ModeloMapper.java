package ar.edu.huergo.aguilar.borassi.tunari.mapper.auto;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearModeloDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.ModeloDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;


@Component
public class ModeloMapper {

    public ModeloDTO toDTO(Modelo modelo) {
        return new ModeloDTO(
            modelo.getId(),
            modelo.getNombre(),
            modelo.getMarca() != null ? modelo.getMarca().getId() : null,
            modelo.getMarca() != null ? modelo.getMarca().getNombre() : null,
            modelo.getVersiones() == null ? List.of() :
                modelo.getVersiones().stream().map(Version::getNombre).toList(),
            modelo.getColores() == null ? List.of() :
                modelo.getColores().stream().map(Color::getNombre).toList()
        );
    }

    public Modelo toEntity(CrearModeloDTO dto) {
        Modelo modelo = new Modelo();
        modelo.setNombre(dto.nombre());          
        return modelo;
    }

    public List<ModeloDTO> toDTOList(List<Modelo> modelos) {
        if (modelos == null) return List.of();
        return modelos.stream().map(this::toDTO).toList();
    }
}