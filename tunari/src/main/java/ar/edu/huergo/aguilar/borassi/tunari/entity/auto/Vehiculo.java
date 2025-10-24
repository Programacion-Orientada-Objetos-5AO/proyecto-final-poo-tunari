package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import java.util.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_vehiculo", discriminatorType = DiscriminatorType.STRING)
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "marca_id", nullable = false)
    @NotNull(message = "La marca no puede estar vacía")
    private Marca marca;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modelo_id", nullable = false)
    @NotNull(message = "El modelo no puede estar vacío")
    private Modelo modelo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "color_id", nullable = false)
    @NotNull(message = "El color no puede estar vacío")
    private Color color;

    @ManyToOne(optional = false)
    @JoinColumn(name = "version_id", nullable = false)
    @NotNull(message = "La versión no puede estar vacía")
    private Version version;

    public Vehiculo(@NotNull Modelo modelo,
                    @NotNull Color color,
                    @NotNull Version version) {
        this.modelo = modelo;
        this.color = color;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehiculo other)) return false;

        return Objects.equals(modelo.getId(), other.modelo.getId()) &&
               Objects.equals(version.getId(), other.version.getId()) &&
               Objects.equals(color.getId(), other.color.getId());
    }
}
