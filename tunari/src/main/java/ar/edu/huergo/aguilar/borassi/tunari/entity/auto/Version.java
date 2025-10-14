package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "versiones",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombreVersion", "marca_id"})
    }
)
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la versión no puede estar vacío")
    @Column(nullable = false)
    private String nombreVersion;

    // Cada versión pertenece a una marca
    @ManyToOne(optional = false)
    @JoinColumn(name = "marca_id", nullable = false)
    @NotNull(message = "La marca a la cual pertenece no puede estar vacía")
    private Marca marca;

    // Lado inverso del ManyToMany con Modelo
    @ManyToMany(mappedBy = "versiones")

    private List<Modelo> modelos;

    public Boolean chequearMarca(Marca marca) {
        return this.marca.equals(marca);
    }
}
