package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "autos")
public class Auto extends Vehiculo {

    private Integer tamanoBaul;

}
