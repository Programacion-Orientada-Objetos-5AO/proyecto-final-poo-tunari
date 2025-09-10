package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "versiones")
public class Version {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la versión no puede estar vacío")
    private String nombreVersion;

    @ManyToOne
    @JoinColumn(name = "marca_id") // Necesitas agregar esta columna a la tabla versiones
    @NotNull(message = "La marca a la cual pertenece no puede estar vacía")
    private Marca marca;
}