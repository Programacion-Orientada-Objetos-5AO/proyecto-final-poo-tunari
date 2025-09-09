package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "versiones")
public class Version {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la versión no puede estar vacío")
    private String nombreVersion;


    @NotBlank(message = "La marca a la cual pertenece no puede estar vacía")
    private Marca marca;

    public Version() { }

}
