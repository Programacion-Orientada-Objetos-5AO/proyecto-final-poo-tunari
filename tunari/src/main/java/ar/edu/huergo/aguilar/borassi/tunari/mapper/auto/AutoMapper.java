package ar.edu.huergo.aguilar.borassi.tunari.mapper.auto;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.AutoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Auto;


@Component
public class AutoMapper {

    public AutoDTO toDTO(Auto auto) {
        return new AutoDTO(
            auto.getId(),
            auto.getMarca(),
            auto.getModelo(),
            auto.getColor(),
            auto.getVersion(),
            auto.getNumeroChasis()
        );
    }

    public List<AutoDTO> toDTOList(List<Auto> autos) {
        return autos.stream()
            .map(this::toDTO)
            .toList();
    }

}