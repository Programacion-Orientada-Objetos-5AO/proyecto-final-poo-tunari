package ar.edu.huergo.aguilar.borassi.tunari.dto.tarea;

public record TareaDTO(
    Long tareaId,
    String nombreTarea,
    String descripcionTarea,
    String creadorTarea,
    boolean completada
) {}