package ar.edu.huergo.aguilar.borassi.tunari.entity.agencia;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Auto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El auto es obligatorio")
    private Auto auto;
    @NotNull(message = "El stock es obligatorio")
    private int stock;

}
