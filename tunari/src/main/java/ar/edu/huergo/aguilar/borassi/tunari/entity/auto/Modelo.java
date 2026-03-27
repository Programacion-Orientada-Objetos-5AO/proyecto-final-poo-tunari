package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "modelos",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre", "marca_id"})
    }
)
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del modelo no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    // Relación con versiones
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "modelo_versiones",
        joinColumns = @JoinColumn(name = "modelo_id"),
        inverseJoinColumns = @JoinColumn(name = "version_id")
    )
    private List<Version> versiones;

    // Relación con colores
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "modelo_colores",
        joinColumns = @JoinColumn(name = "modelo_id"),
        inverseJoinColumns = @JoinColumn(name = "color_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"modelo_id", "color_id"})
    )
    private List<Color> colores;

    
    public void validarVehiculo(Color color, Version version) {
        if (this.colores.contains(color) && this.versiones.contains(version)) {
            return;
        } else {
            throw new IllegalArgumentException("La version o el color no pertenecen al modelo");
        }
    }
}