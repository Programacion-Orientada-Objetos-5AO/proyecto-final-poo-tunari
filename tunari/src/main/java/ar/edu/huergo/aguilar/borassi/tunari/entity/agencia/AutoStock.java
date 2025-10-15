package ar.edu.huergo.aguilar.borassi.tunari.entity.agencia;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Auto;
import jakarta.persistence.Column;
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
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auto_stock")
public class AutoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El auto es obligatorio")
    @ManyToOne(optional = false)
    @JoinColumn(name = "auto_id", nullable = false)
    private Auto auto;

    @NotNull(message = "El stock es obligatorio")
    @Column(nullable = false)
    private int stock;

    public AutoStock(@NotNull Auto auto, @NotNull int stock) {
        this.auto = auto;
        this.stock = stock;
    }
}
