package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import java.util.List;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

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

    @NotBlank(message = "El modelo no puede estar vacío")
    private String modelo;

    @ManyToMany
    @JoinTable(name = "autos_colores", 
            joinColumns = @JoinColumn(name = "auto_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id"))
    private List<Color> colores;

    public Auto() { }

    public Auto(String marca) {
        this.marca = marca;
    }

    public Auto(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }

}
