package ar.edu.huergo.aguilar.borassi.tunari.mapper.agencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.MostrarAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.CrearAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.Agencia;


@Component
public class AgenciaMapper {

    @Autowired
    private AutoStockMapper autoStockMapper;
    public MostrarAgenciaDTO toDTO(Agencia agencia) {
        return new MostrarAgenciaDTO(
            agencia.getId(),
            agencia.getNombre(),
            agencia.getUbicacion(),
            autoStockMapper.toDTOList(agencia.getListaAutos()),
            agencia.getMarca().getNombre()
        );
    }
    
    public List<MostrarAgenciaDTO> toDTOList(List<Agencia> agencias) {
        return agencias.stream()
            .map(this::toDTO)
            .toList();
    }

    public Agencia toEntity(CrearAgenciaDTO dto) {
        Agencia agencia = new Agencia();
        agencia.setNombre(dto.nombre());
        agencia.setUbicacion(dto.ubicacion());
        return agencia;
    }

}
