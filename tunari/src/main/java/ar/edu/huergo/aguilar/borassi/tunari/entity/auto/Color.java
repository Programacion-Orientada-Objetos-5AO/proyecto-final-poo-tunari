package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "colores",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombreColor", "marca_id"})
    }
)
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del color no puede estar vacío")
    @Column(nullable = false)
    private String nombreColor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "marca_id", nullable = false)
    @NotNull(message = "La marca a la cual pertenece no puede estar vacía")
    private Marca marca;
}
