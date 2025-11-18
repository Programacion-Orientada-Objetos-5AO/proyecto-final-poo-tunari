package ar.edu.huergo.aguilar.borassi.tunari.service.tarea;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.entity.tarea.Tarea;
import ar.edu.huergo.aguilar.borassi.tunari.repository.tarea.TareaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    public List<Tarea> obtenerTodasLasTareas() {
        return tareaRepository.findAll();
    }

    public Tarea obtenerTareaPorId(Long id) throws EntityNotFoundException {
        return tareaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
    }

    public Tarea crearTarea(Tarea tarea) throws EntityNotFoundException {;
        return tareaRepository.save(tarea);
    }

    public Tarea actualizarTarea(Long id, String nombreTarea, String descripcionTarea, String creadorTarea) throws EntityNotFoundException {
        Tarea tareaExistente = obtenerTareaPorId(id);
        tareaExistente.setNombreTarea(nombreTarea);
        tareaExistente.setDescripcionTarea(descripcionTarea);
        tareaExistente.setCreadorTarea(creadorTarea);
        return tareaRepository.save(tareaExistente);
    }
    
    public void eliminarTarea(Long id) throws EntityNotFoundException {
        Tarea tarea = obtenerTareaPorId(id);
        tareaRepository.delete(tarea);
    }

    public List<Tarea> resolverTarea(List<Long> tareasIds) throws IllegalArgumentException, EntityNotFoundException {
        if (tareasIds == null || tareasIds.isEmpty()) {
            throw new IllegalArgumentException("Hay que ingresar una tarea.");
        }
        List<Tarea> tareas = tareaRepository.findAllById(tareasIds);
        if (tareas.size() != tareas.stream().filter(Objects::nonNull).distinct()
                .count()) {
            throw new EntityNotFoundException("Una o más tareas no existen. Intente nuevamente.");
        }
        return tareas;
    }
}