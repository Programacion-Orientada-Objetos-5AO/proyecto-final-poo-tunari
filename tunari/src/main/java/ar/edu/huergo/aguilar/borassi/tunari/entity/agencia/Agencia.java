package ar.edu.huergo.aguilar.borassi.tunari.entity.agencia;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agencias")
public class Agencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre de la agencia no puede estar vacío")
    private String nombreAgencia;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    @NotNull(message = "La marca no puede estar vacía")
    private Marca marca;

}
