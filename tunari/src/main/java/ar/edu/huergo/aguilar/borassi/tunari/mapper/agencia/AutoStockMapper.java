package ar.edu.huergo.aguilar.borassi.tunari.mapper.agencia;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.MostrarAutoStockDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.AutoStock;


@Component
public class AutoStockMapper {

    public MostrarAutoStockDTO toDTO(AutoStock autoStock) {
        return new MostrarAutoStockDTO(
            autoStock.getAuto(),
            autoStock.getStock()
        );
    }

    public List<MostrarAutoStockDTO> toDTOList(List<AutoStock> autoStocks) {
        return autoStocks.stream()
            .map(this::toDTO)
            .toList();
    }

}