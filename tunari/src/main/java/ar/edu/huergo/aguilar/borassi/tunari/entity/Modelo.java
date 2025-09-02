package ar.edu.huergo.aguilar.borassi.tunari.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
        @JoinTable(name = "autos_modelos", // usa la misma tabla que Marca
        joinColumns = @JoinColumn(name = "modelo_id"),
        inverseJoinColumns = @JoinColumn(name = "marca_id"))
        private Marca marca;

        @OneToMany
        @JoinTable(name = "modelo_versiones",
        joinColumns = @JoinColumn(name = "modelo_id"),
        inverseJoinColumns = @JoinColumn(name = "version_id"))
        private List<Version> versiones;

        @OneToMany
        @JoinTable(name = "modelo_colores",
        joinColumns = @JoinColumn(name = "modelo_id"),
        inverseJoinColumns = @JoinColumn(name = "color_id"))
        private List<Color> colores;

}