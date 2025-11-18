package ar.edu.huergo.aguilar.borassi.tunari.entity.tarea;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la tarea no puede estar vacío")
    private String nombreTarea;
    
    @NotBlank(message = "La descripcion de la tarea no puede estar vacía")
    private String descripcionTarea;

    @NotBlank(message = "El creador de la tarea no puede estar vacío")
    private String creadorTarea;

    @NotNull(message = "El estado de la tarea no puede estar vacío")
    private boolean completada;

}
