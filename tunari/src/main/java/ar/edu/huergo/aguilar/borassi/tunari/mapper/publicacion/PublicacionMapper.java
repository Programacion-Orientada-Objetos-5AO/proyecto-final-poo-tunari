package ar.edu.huergo.aguilar.borassi.tunari.mapper.publicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.ModeloDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.publicacion.CrearPublicacionDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.publicacion.MostrarPublicacionDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.publicacion.Publicacion;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.auto.ModeloMapper;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;

@Component
public class PublicacionMapper {

    @Autowired
    private ModeloMapper modeloMapper;

    public MostrarPublicacionDTO toDTO(Publicacion publicacion) {
        ModeloDTO modeloDTO = modeloMapper.toDTO(publicacion.getModelo());
        MostrarPublicacionDTO publicacionDTO = new MostrarPublicacionDTO(modeloDTO);
        return publicacionDTO;
    }


    public List<MostrarPublicacionDTO> toDTOList(List<Publicacion> publicaciones) {
        if (publicaciones == null) return List.of();
        return publicaciones.stream().map(this::toDTO).toList();
    }
}
