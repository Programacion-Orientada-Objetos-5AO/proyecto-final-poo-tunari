package ar.edu.huergo.aguilar.borassi.tunari.mapper.auto;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearMarcaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.MarcaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;

@Component
public class MarcaMapper {


    public MarcaDTO toDTO(Marca marca) {
        var modelos = marca.getModelos() == null ? List.<Modelo>of() : marca.getModelos();
        var colores = marca.getColores() == null ? List.<Color>of() : marca.getColores();

        return new MarcaDTO(
            marca.getId(),
            marca.getNombre(),
            modelos.stream().map(Modelo::getNombre).toList(),  
            colores.stream().map(Color::getNombre).toList()     
        );
    }

    public Marca toEntity(CrearMarcaDTO dto) {
        Marca marca = new Marca();
        marca.setNombre(dto.nombreMarca());
        return marca;
    }

    public List<MarcaDTO> toDTOList(List<Marca> marcas) {
        if (marcas == null) return List.of();
        return marcas.stream().map(this::toDTO).toList();
    }
}
