package ar.edu.huergo.aguilar.borassi.tunari.mapper.calculadora;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.calculadora.RequestCalculoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.calculadora.ResponseCalculoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.calculadora.Calculo;

@Component
public class CalculoMapper {

    public ResponseCalculoDTO toDTO(Calculo calculo) {
        return new ResponseCalculoDTO(
            calculo.getId(),
            calculo.getOperacion(),
            calculo.getParametroUno(),
            calculo.getParametroDos(),
            calculo.getResultado()
        );
    }

    public Calculo toEntity(RequestCalculoDTO calculoDTO) {
        Calculo calculo = new Calculo();
        calculo.setOperacion(calculoDTO.operacion());
        calculo.setParametroUno(calculoDTO.parametroUno());
        calculo.setParametroDos(calculoDTO.parametroDos());
        return calculo;
    }
    public List<ResponseCalculoDTO> toDTOList(List<Calculo> calculos) {
        return calculos.stream()
            .map(this::toDTO)
            .toList();
    }

}