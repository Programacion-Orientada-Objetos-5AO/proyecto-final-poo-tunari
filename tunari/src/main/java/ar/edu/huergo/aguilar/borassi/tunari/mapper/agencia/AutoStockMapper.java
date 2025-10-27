package ar.edu.huergo.aguilar.borassi.tunari.mapper.agencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.MostrarAutoStockDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.AutoStock;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.auto.VehiculoMapper;


@Component
public class AutoStockMapper {
    @Autowired
    private VehiculoMapper vehiculoMapper;

    public MostrarAutoStockDTO toDTO(AutoStock autoStock) {
        return new MostrarAutoStockDTO(
            vehiculoMapper.toDTO(autoStock.getAuto()),
            autoStock.getStock()
        );
    }

    public List<MostrarAutoStockDTO> toDTOList(List<AutoStock> autoStocks) {
        return autoStocks.stream()
            .map(this::toDTO)
            .toList();
    }

}