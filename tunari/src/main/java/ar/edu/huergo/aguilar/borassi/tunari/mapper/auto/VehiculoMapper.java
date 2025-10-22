package ar.edu.huergo.aguilar.borassi.tunari.mapper.auto;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.VehiculoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Vehiculo;


@Component
public class VehiculoMapper {

    public VehiculoDTO toDTO(Vehiculo auto) {
        return new VehiculoDTO(
            auto.getId(),
            auto.getMarca().getNombre(),
            auto.getModelo().getNombre(),
            auto.getColor().getNombre(),
            auto.getVersion().getNombre()
        );
    }

    public List<VehiculoDTO> toDTOList(List<Vehiculo> autos) {
        return autos.stream()
            .map(this::toDTO)
            .toList();
    }

}