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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "autos")
public class Auto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La marca no puede estar vacía")
    private String marca;

    @ManyToOne
    @JoinColumn(name = "modelo_id")
    @NotNull(message = "El modelo no puede estar vacío")
    private Modelo modelo;

    @ManyToMany
    @JoinTable(name = "autos_colores", 
            joinColumns = @JoinColumn(name = "auto_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id"))
    private List<Color> colores;

    @ManyToMany
    @JoinTable(name = "autos_versiones", 
            joinColumns = @JoinColumn(name = "auto_id"),
            inverseJoinColumns = @JoinColumn(name = "version_id"))
    private List<Version> versiones;

}
