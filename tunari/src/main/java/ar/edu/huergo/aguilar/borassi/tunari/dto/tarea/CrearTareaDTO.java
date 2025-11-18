package ar.edu.huergo.aguilar.borassi.tunari.dto.tarea;

import jakarta.validation.constraints.NotBlank;

public record CrearTareaDTO(
    @NotBlank(message = "El nombre de la tarea no puede estar vacío")
    String nombreTarea,

    @NotBlank(message = "La descripción de la tarea no puede estar vacía")
    String descripcionTarea,

    @NotBlank(message = "El creador de la tarea no puede estar vacío")
    String creadorTarea,

    @NotBlank(message = "El estado de la tarea no puede estar vacío")
    boolean completada

) {}
