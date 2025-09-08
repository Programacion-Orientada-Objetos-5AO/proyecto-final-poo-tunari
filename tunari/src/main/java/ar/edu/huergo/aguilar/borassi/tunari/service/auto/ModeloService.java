package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.*;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;

    public List<Modelo> obtenerTodosLosModelos() {
        return modeloRepository.findAll();
    }

    public Modelo obtenerModeloPorId(Long id) throws EntityNotFoundException {
        return modeloRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Modelo no encontrado."));
    }

    public Modelo crearModelo(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    public Modelo actualizarModelo(Long id, Modelo modelo) throws EntityNotFoundException {
        Modelo modeloExistente = obtenerModeloPorId(id);
        modeloExistente.setNombreModelo(modelo.getNombreModelo());
        return modeloRepository.save(modeloExistente);
    }
    
    public void eliminarModelo(Long id) throws EntityNotFoundException {
        Modelo modelo = obtenerModeloPorId(id);
        modeloRepository.delete(modelo);
    }

    public List<Modelo> obtenerModeloPorNombre(String nombreModelo) {
        return modeloRepository.findByNombreContainingIgnoreCase(nombreModelo);
    }

    public List<Modelo> resolverModelo(List<Long> modelosIds) throws IllegalArgumentException, EntityNotFoundException {
        if (modelosIds == null || modelosIds.isEmpty()) {
            throw new IllegalArgumentException("Hay que ingresar un modelo.");
        }
        List<Modelo> modelos = modeloRepository.findAllById(modelosIds);
        if (modelos.size() != modelosIds.stream().filter(Objects::nonNull).distinct()
                .count()) {
            throw new EntityNotFoundException("Uno o más modelos no existen. Intente nuevamente.");
        }
        return modelos;
    }
}