package ar.edu.huergo.aguilar.borassi.tunari.mapper;

import ar.edu.huergo.aguilar.borassi.tunari.dto.VersionDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.Version;
import ar.edu.huergo.aguilar.borassi.tunari.dto.CrearVersionDTO;

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