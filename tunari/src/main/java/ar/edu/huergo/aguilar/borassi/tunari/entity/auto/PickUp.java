package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "autosDeportivos")
public class PickUp extends Vehiculo {

    private int pesoBaul;

}
