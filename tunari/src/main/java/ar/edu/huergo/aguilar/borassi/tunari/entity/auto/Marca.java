package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "marcas")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la marca no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre de la marca debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, unique = true)
    private String nombreMarca;

    @OneToMany(mappedBy = "marca")
    private List<Modelo> modelos;

    @OneToMany(mappedBy = "marca")
    private List<Version> versiones;

    @OneToMany(mappedBy = "marca")
    private List<Color> colores;
}
