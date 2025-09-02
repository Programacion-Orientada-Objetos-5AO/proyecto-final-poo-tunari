package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "marcas")
public class Marca {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "El nombre de la marca no puede estar vacío")
        private String nombreMarca;

        @OneToMany
        @JoinTable(name = "autos_modelos", 
                joinColumns = @JoinColumn(name = "marca_id"),
                inverseJoinColumns = @JoinColumn(name = "modelo_id"))
        private List<Modelo> modelos;

        @OneToMany
        @JoinTable(name = "autos_colores", 
                joinColumns = @JoinColumn(name = "marca_id"),
                inverseJoinColumns = @JoinColumn(name = "color_id"))
        private List<Color> colores;

        public Marca() { }

}