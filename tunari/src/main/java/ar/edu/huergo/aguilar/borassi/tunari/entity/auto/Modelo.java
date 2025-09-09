package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modelos")
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del modelo no puede estar vacío")
    private String nombreModelo;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @ManyToMany
    @JoinTable(name = "modelo_versiones",
        joinColumns = @JoinColumn(name = "modelo_id"),
        inverseJoinColumns = @JoinColumn(name = "version_id"))
    private List<Version> versiones;

    @ManyToMany
    @JoinTable(name = "modelo_colores",
        joinColumns = @JoinColumn(name = "modelo_id"),
        inverseJoinColumns = @JoinColumn(name = "color_id"))
    private List<Color> colores;
}