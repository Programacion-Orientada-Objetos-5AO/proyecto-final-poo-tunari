package ar.edu.huergo.aguilar.borassi.tunari.mapper.auto;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearVersionDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.VersionDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;


public class VersionMapper {

    public static VersionDTO toDTO(Version version) {
        return new VersionDTO(
            version.getId(),
            version.getNombreMarca(),
            version.getNombreVersion()
        );
    }

    public static Version toEntity(CrearVersionDTO dto, Modelo modelo, Color color) {
        Version version = new Version();
        version.setNombreVersion(dto.nombreVersion());
        version.setNombreMarca(dto.marcaId().toString());
        return version;
    }
}