package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

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
    private String nombreMarca;

    public Version() { }

}
