package ar.edu.huergo.aguilar.borassi.tunari.entity.agencia;

import java.util.ArrayList;
import java.util.List;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Vehiculo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agencias")
public class Agencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotNull(message = "La ubicacion es obligatoria")
    @Column(nullable = false)
    private String ubicacion;


    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JoinColumn(name = "agencia_id", nullable = false)
    private List<AutoStock> listaAutos = new ArrayList<>();


    @NotNull
    @ManyToOne()
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    public Agencia(@NotNull String nombre, @NotNull String ubicacion, @NotNull Marca marca) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.marca = marca;
    }

    public void modificarStock(Vehiculo auto, int stock) {
        if (auto.getMarca() != this.marca) {
            throw new IllegalArgumentException("El auto no pertenece a la marca de la agencia.");
        }
        for (AutoStock as : this.listaAutos) {
            if (as.getAuto().equals(auto)) {
                as.setStock(stock);
                return;
            }
        }
        this.listaAutos.add(new AutoStock(auto, stock));
    }
}
