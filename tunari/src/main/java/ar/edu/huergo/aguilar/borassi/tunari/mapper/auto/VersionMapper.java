package ar.edu.huergo.aguilar.borassi.tunari.mapper.auto;

import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.VersionDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;
import java.util.List;

@Component
public class VersionMapper {

    public VersionDTO toDTO(Version version) {
        return new VersionDTO(
            version.getId(),
            version.getMarca().getNombreMarca(),
            version.getNombreVersion()
        );
    }

    public Version toEntity(VersionDTO dto) {
        Version version = new Version();
        version.setNombreVersion(dto.nombreVersion());
        return version;
    }

    public List<VersionDTO> toDTOList(List<Version> versiones) {
        if (versiones == null) return List.of();
        return versiones.stream().map(this::toDTO).toList();
    }
}