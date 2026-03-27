package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "versiones",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre", "marca_id"})
    }
)
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la versión no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    // Cada versión pertenece a una marca
    @ManyToOne(optional = false)
    @JoinColumn(name = "marca_id", nullable = false)
    @NotNull(message = "La marca a la cual pertenece no puede estar vacía")
    private Marca marca;

    // Lado inverso del ManyToMany con Modelo
    @ManyToMany(mappedBy = "versiones")

    private List<Modelo> modelos;

    public Boolean validarMarca(Marca marca) {
        return this.marca.equals(marca);
    }
}
