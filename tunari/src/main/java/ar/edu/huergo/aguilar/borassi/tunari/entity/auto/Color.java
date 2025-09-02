package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "colores")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del color no puede estar vacío")
    private String nombreColor;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @OneToMany
    @JoinTable(name = "color_modelos",
            joinColumns = @JoinColumn(name = "color_id"),
            inverseJoinColumns = @JoinColumn(name = "modelo_id"))
    private List<Modelo> modelos;
}

