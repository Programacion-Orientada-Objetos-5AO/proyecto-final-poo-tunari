package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.*;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;
import jakarta.persistence.EntityNotFoundException;

@Service
public class VersionService {
    @Autowired
    private VersionRepository versionRepository;

    public List<Version> obtenerTodasLasVersiones() {
        return versionRepository.findAll();
    }

    public Version obtenerVersionPorId(Long id) throws EntityNotFoundException {
        return versionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Version no encontrada."));
    }

    public Version crearVersion(Version version) {
        return versionRepository.save(version);
    }

    public Version actualizarVersion(Long id, Version version) throws EntityNotFoundException {
        Version versionExistente = obtenerVersionPorId(id);
        versionExistente.setNombreVersion(version.getNombreVersion());
        return versionRepository.save(versionExistente);
    }
    
    public void eliminarVersion(Long id) throws EntityNotFoundException {
        Version version = obtenerVersionPorId(id);
        versionRepository.delete(version);
    }

    public List<Version> obtenerVersionesPorNombre(String nombreVersion) {
        return versionRepository.findByNombreContainingIgnoreCase(nombreVersion);
    }

    public List<Version> resolverVersion(List<Long> versionesIds) throws IllegalArgumentException, EntityNotFoundException {
        if (versionesIds == null || versionesIds.isEmpty()) {
            throw new IllegalArgumentException("Hay que ingresar una versión.");
        }
        List<Version> versiones = versionRepository.findAllById(versionesIds);
        if (versiones.size() != versionesIds.stream().filter(Objects::nonNull).distinct()
                .count()) {
            throw new EntityNotFoundException("Una o más versiones no existen. Intente nuevamente.");
        }
        return versiones;
    }
}