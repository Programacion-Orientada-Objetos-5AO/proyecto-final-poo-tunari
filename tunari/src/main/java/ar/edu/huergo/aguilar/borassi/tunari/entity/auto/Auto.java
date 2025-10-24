package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("AUTO") 
public class Auto extends Vehiculo {

    private Integer tamanoBaul;
}

