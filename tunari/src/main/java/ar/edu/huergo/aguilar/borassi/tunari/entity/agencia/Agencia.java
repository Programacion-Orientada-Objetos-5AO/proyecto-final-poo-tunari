package ar.edu.huergo.aguilar.borassi.tunari.entity.agencia;

import java.util.List;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "La ubicacion es obligatorio")
    private String ubicacion;

    @OneToMany
    @JoinColumn(name = "agencia_id")  // Esta columna estará en la tabla AutoStock
    private List<AutoStock> listaAutos;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    public Agencia(@NotNull(message = "El nombre es obligatorio") String nombre,
            @NotNull(message = "La ubicacion es obligatorio") String ubicacion, Marca marca) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.marca = marca;
    }

    
    
    
}
