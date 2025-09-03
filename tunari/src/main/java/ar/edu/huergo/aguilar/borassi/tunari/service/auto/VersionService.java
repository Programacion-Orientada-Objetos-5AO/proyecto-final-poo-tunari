package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.lang.Runtime.Version;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.*;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.*;
import jakarta.persistence.EntityNotFoundException;

@Service
public class VersionService {
    @Autowired
    private VersionRepository versionRepository;

    public List<Version> obtenerTodasLasVersiones() {
        return versionRepository.findAll();
    }

    public Ingrediente obtenerIngredientePorId(Long id) throws EntityNotFoundException {
        return ingredienteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Ingrediente no encontrado"));
    }

    public Version crearIngrediente(Version version) {
        return versionRepository.save(version);
    }

    public Ingrediente actualizarIngrediente(Long id, Ingrediente ingrediente) throws EntityNotFoundException {
        Ingrediente ingredienteExistente = obtenerIngredientePorId(id);
        ingredienteExistente.setNombre(ingrediente.getNombre());
        return ingredienteRepository.save(ingredienteExistente);
    }
    
    public void eliminarIngrediente(Long id) throws EntityNotFoundException {
        Ingrediente ingrediente = obtenerIngredientePorId(id);
        ingredienteRepository.delete(ingrediente);
    }

    public List<Ingrediente> obtenerIngredientesPorNombre(String nombre) {
        return ingredienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Ingrediente> resolverIngredientes(List<Long> ingredientesIds) throws IllegalArgumentException, EntityNotFoundException {
        if (ingredientesIds == null || ingredientesIds.isEmpty()) {
            throw new IllegalArgumentException("Debe especificar al menos un ingrediente");
        }
        List<Ingrediente> ingredientes = ingredienteRepository.findAllById(ingredientesIds);
        if (ingredientes.size() != ingredientesIds.stream().filter(Objects::nonNull).distinct()
                .count()) {
            throw new EntityNotFoundException("Uno o más ingredientes no existen");
        }
        return ingredientes;
    }
}
