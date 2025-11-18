package ar.edu.huergo.aguilar.borassi.tunari.mapper.tarea;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.tarea.TareaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.tarea.CrearTareaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.tarea.Tarea;

@Component
public class TareaMapper {

    public TareaDTO toDTO(Tarea tarea) {
        return new TareaDTO(
            tarea.getId(),
            tarea.getNombreTarea(),
            tarea.getDescripcionTarea(),
            tarea.getCreadorTarea(),
            tarea.isCompletada()
        );
    }

    public Tarea toEntity(CrearTareaDTO tareaDTO) {
        Tarea tarea = new Tarea();
        tarea.setNombreTarea(tareaDTO.nombreTarea());
        tarea.setCompletada(tareaDTO.completada());
        tarea.setDescripcionTarea(tareaDTO.descripcionTarea());
        tarea.setCreadorTarea(tareaDTO.creadorTarea());
        boolean completada = tarea.isCompletada();
        System.out.print(completada);
        return tarea;
    }

    public List<TareaDTO> toDTOList(List<Tarea> tareas) {
        return tareas.stream()
            .map(this::toDTO)
            .toList();
    }

}